package com.syfri.userservice.dao;

import com.syfri.baseapi.dao.BaseDAO;
import com.syfri.userservice.model.*;

import java.util.List;

public interface RoleDAO extends BaseDAO<RoleVO>{

	/*--根据用户取角色.--*/
	List<RoleVO> doFindRoleByUserid(String userid);

	/*--获取角色，如果roleVO为null，则获取所有角色.--*/
	List<RoleVO> doFindRoleResources(RoleVO roleVO);

	/*--新增角色时批量插入用户资源(中间表).--*/
	int doInsertRoleResourcesBatch(List<RoleResourceVO> roleResources);

	/*--修改角色时先删除角色资源数据(中间表).--*/
	int doDeleteRoleResources(String roleid);

	/*--删除角色时修改用户中间表数据--*/
	int doDeleteRoleResourcesBatch(String roleid);
}