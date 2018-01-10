package com.syfri.userservice.service;

import com.syfri.baseapi.service.BaseService;
import com.syfri.userservice.model.PermissionVO;
import com.syfri.userservice.model.ResourceTree;
import com.syfri.userservice.model.ResourceVO;
import com.syfri.userservice.model.RoleVO;

import java.util.List;

public interface ResourceService  extends BaseService<ResourceVO>{

	/*--根据用户角色列表查询查询用户资源.--*/
	List<ResourceVO> doFindResourceByRoleList(List<RoleVO> roleList);

	/*--查询：获取资源权限，如果resourceVO为null，则获取所有资源.--*/
	List<ResourceVO> doFindResourcePermissions(ResourceVO resourceVO);

	/*--新增：增加资源并为用户赋予权限.--*/
	ResourceVO doInsertResourcePermissions(ResourceVO resourceVO);

	/*--修改：修改资源并修改资源权限.--*/
	ResourceVO doUpdateResourcePermissions(ResourceVO resourceVO);

	/*--删除：删除资源同时删除其权限.--*/
	void doDeleteResourcePermissions(String resourceid);

	/*--向角色资源中间表中批量增加数据.--*/
	int insertResourcePermissionsBatch(String resourceid, List<PermissionVO> permissions);

	/*--根据角色列表构造资源树状结构.--*/
	List<ResourceTree> getResourceTree(List<RoleVO> roleList);

	/*--将ResourceVO对象转换成ResourceTree对象.--*/
	ResourceTree getResourceToTree(ResourceVO resourceVO);
}