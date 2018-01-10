package com.syfri.userservice.service.impl;

import com.syfri.userservice.model.*;
import com.syfri.userservice.utils.CurrentUserUtil;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.syfri.baseapi.service.impl.BaseServiceImpl;
import com.syfri.userservice.dao.ResourceDAO;
import com.syfri.userservice.service.ResourceService;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
@Service("resourceService")
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
public class ResourceServiceImpl extends BaseServiceImpl<ResourceVO> implements ResourceService {

	@Autowired
	private ResourceDAO resourceDAO;

	@Override
	public ResourceDAO getBaseDAO() {
		return resourceDAO;
	}

	/*--根据用户角色列表查询查询用户资源.--*/
	@Override
	public List<ResourceVO> doFindResourceByRoleList(List<RoleVO> roleList){
		return resourceDAO.doFindResourceByRoleList(roleList);
	}

	/*--查询：获取资源权限，如果resourceVO为null，则获取所有资源.--*/
	@Override
	public List<ResourceVO> doFindResourcePermissions(ResourceVO resourceVO){
		return resourceDAO.doFindResourcePermissions(resourceVO);
	}

	/*--新增：增加资源并为用户赋予权限.--*/
	@Override
	public ResourceVO doInsertResourcePermissions(ResourceVO resourceVO){

		//向资源表SYS_RESOURCE中插入数据
		resourceVO.setCreateId(CurrentUserUtil.getCurrentUserId());
		resourceVO.setCreateName(CurrentUserUtil.getCurrentUserName());
		resourceDAO.doInsertByVO(resourceVO);

		//向资源权限表SYS_RESOURCE_PERMISSION中插入数据
		((ResourceService)AopContext.currentProxy()).insertResourcePermissionsBatch(resourceVO.getResourceid(), resourceVO.getPermissions());

		return resourceVO;
	}

	/*--修改：修改资源并修改资源权限.--*/
	@Override
	public ResourceVO doUpdateResourcePermissions(ResourceVO resourceVO){

		//修改资源表SYS_RESOURCE数据
		resourceVO.setAlterId(CurrentUserUtil.getCurrentUserId());
		resourceVO.setAlterName(CurrentUserUtil.getCurrentUserName());
		resourceDAO.doUpdateByVO(resourceVO);

		//修改资源权限表SYS_RESOURCE_PERMISSION中数据，先删除再新增
		resourceDAO.doDeleteResourcePermissions(resourceVO.getResourceid());
		((ResourceService)AopContext.currentProxy()).insertResourcePermissionsBatch(resourceVO.getResourceid(), resourceVO.getPermissions());

		return resourceVO;
	}

	/*--删除：删除资源同时删除其权限.--*/
	@Override
	public void doDeleteResourcePermissions(String resourceid){
		//删除资源表SYS_RESOURCE数据
		resourceDAO.doDeleteById(resourceid);
		//删除资源权限表SYS_RESOURCE_PERMISSION数据
		resourceDAO.doDeleteResourcePermissionsBatch(resourceid);
	}

	/*--向角色资源中间表中批量增加数据.--*/
	@Override
	public int insertResourcePermissionsBatch(String resourceid, List<PermissionVO> permissions){
		List<ResourcePermissionVO> list = new ArrayList<>();
		if(permissions!=null && permissions.size()>0){
			for(PermissionVO permission : permissions){
				ResourcePermissionVO temp = new ResourcePermissionVO();
				temp.setResourceid(resourceid);
				temp.setPermissionid(permission.getPermissionid());
				temp.setCreateId(CurrentUserUtil.getCurrentUserId());
				temp.setCreateName(CurrentUserUtil.getCurrentUserName());
				list.add(temp);
			}
			return resourceDAO.doInsertResourcePermissionsBatch(list);
		}else{
			return -1;
		}
	}

	/*--根据角色列表构造资源树状结构*/
	@Override
	public List<ResourceTree> getResourceTree(List<RoleVO> roleList){
		List<ResourceTree> trees = new ArrayList<>();

		//获取角色列表下的父资源
		Map map = new HashMap<>();
		map.put("roleList", roleList);
		map.put("parentid", "-1");
		List<ResourceVO> resources = resourceDAO.doFindResourceByParentId(map);

		//根据父资源获取子资源
		if(resources!=null && resources.size()>0){
			for(ResourceVO resource : resources){
				ResourceTree tree = ((ResourceService)AopContext.currentProxy()).getResourceToTree(resource);

				//获取父资源的子资源
				List<ResourceTree> children = new ArrayList<>();
				map.put("parentid", resource.getResourceid());
				List<ResourceVO> temps = resourceDAO.doFindResourceByParentId(map);
				if(temps!=null && temps.size()>0){
					for(ResourceVO temp : temps){
						ResourceTree child = ((ResourceService)AopContext.currentProxy()).getResourceToTree(temp);
						children.add(child);
					}
				}
				tree.setChildren(children);
				trees.add(tree);
			}
		}
		return trees;
	}

	/*--将ResourceVO对象转换成ResourceTree对象.--*/
	@Override
	public ResourceTree getResourceToTree(ResourceVO resourceVO){
		return new ResourceTree(resourceVO.getResourceid(), resourceVO.getResourcename(), resourceVO.getResourceinfo(), resourceVO.getSeqno(), resourceVO.getIcon(), resourceVO.getType());
	}
}