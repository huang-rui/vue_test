package com.syfri.userservice.service.impl;

import com.syfri.userservice.model.*;
import com.syfri.userservice.utils.CurrentUserUtil;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.syfri.baseapi.service.impl.BaseServiceImpl;
import com.syfri.userservice.dao.RoleDAO;
import com.syfri.userservice.service.RoleService;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
@Service("roleService")
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
public class RoleServiceImpl extends BaseServiceImpl<RoleVO> implements RoleService {

	@Autowired
	private RoleDAO roleDAO;

	@Override
	public RoleDAO getBaseDAO() {
		return roleDAO;
	}

	@Override
	public List<RoleVO> doFindRoleByUserid(String userid) {
		return roleDAO.doFindRoleByUserid(userid);
	}

	/*--查询：获取用户资源，如果roleVO为null，则获取所有用户资源.--*/
	@Override
	public List<RoleVO> doFindRoleResources(RoleVO roleVO){
		return roleDAO.doFindRoleResources(roleVO);
	}

	/*--新增：增加角色并为用户赋予资源.--*/
	@Override
	public RoleVO doInsertRoleResources(RoleVO roleVO){

		//向角色表SYS_ROLE中增加数据
		roleVO.setCreateId(CurrentUserUtil.getCurrentUserId());
		roleVO.setCreateName(CurrentUserUtil.getCurrentUserName());
		roleDAO.doInsertByVO(roleVO);

		//向角色资源中间表SYS_ROLE_RESOURCE中增加数据
		((RoleService)AopContext.currentProxy()).insertRoleResourcesBatch(roleVO.getRoleid(), roleVO.getResources());

		return roleVO;
	}

	/*--修改：修改角色并修改用户资源.--*/
	@Override
	public RoleVO doUpdateRoleResources(RoleVO roleVO) {

		//修改角色表SYS_ROLE数据
		roleVO.setAlterId(CurrentUserUtil.getCurrentUserId());
		roleVO.setAlterName(CurrentUserUtil.getCurrentUserName());
		roleDAO.doUpdateByVO(roleVO);

		//修改角色资源中间表SYS_ROLE_RESOURCE数据
		String roleid = roleVO.getRoleid();
		roleDAO.doDeleteRoleResources(roleid);
		((RoleService)AopContext.currentProxy()).insertRoleResourcesBatch(roleid, roleVO.getResources());

		return roleVO;
	}

	/*--删除：删除角色同时删除其资源.--*/
	@Override
	public void doDeleteRole(String roleid){
		//输出角色表SYS_ROLE
		roleDAO.doDeleteById(roleid);
		//删除角色资源中间表SYS_ROLE_RESOURCE
		roleDAO.doDeleteRoleResourcesBatch(roleid);
	}

	/*批量向角色权限中间表插入数据.*/
	@Override
	public int insertRoleResourcesBatch(String roleid, List<ResourceVO> resources){
		List<RoleResourceVO> list = new ArrayList<>();
		if(resources!=null && resources.size()>0){
			for(ResourceVO resource : resources){
				RoleResourceVO temp = new RoleResourceVO();
				temp.setRoleid(roleid);
				temp.setResourceid(resource.getResourceid());
				temp.setCreateId(CurrentUserUtil.getCurrentUserId());
				temp.setCreateName(CurrentUserUtil.getCurrentUserName());
				list.add(temp);
			}
			return roleDAO.doInsertRoleResourcesBatch(list);
		}else{
			return -1;
		}
	}
}