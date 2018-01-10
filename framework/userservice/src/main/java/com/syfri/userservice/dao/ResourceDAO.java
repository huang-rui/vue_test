package com.syfri.userservice.dao;

import com.syfri.baseapi.dao.BaseDAO;
import com.syfri.userservice.model.ResourcePermissionVO;
import com.syfri.userservice.model.ResourceVO;
import com.syfri.userservice.model.RoleVO;

import java.util.List;
import java.util.Map;

public interface ResourceDAO extends BaseDAO<ResourceVO>{

	/*--根据用户角色列表查询查询用户资源.--*/
	List<ResourceVO> doFindResourceByRoleList(List<RoleVO> roleList);

	/*--获取资源，如果ResourceVO为null，则获取所有资源.--*/
	List<ResourceVO> doFindResourcePermissions(ResourceVO resourceVO);

	/*--新增资源时批量插入资源权限(中间表).--*/
	int doInsertResourcePermissionsBatch(List<ResourcePermissionVO> roleResources);

	/*--修改资源时先删除资源权限数据(中间表).--*/
	int doDeleteResourcePermissions(String resourceid);

	/*--删除资源时删除资源权限数据(中间表)--*/
	int doDeleteResourcePermissionsBatch(String resourceid);

	/*--通过用户角色列表、父节点获取资源列表.--*/
	List<ResourceVO> doFindResourceByParentId(Map map);
}