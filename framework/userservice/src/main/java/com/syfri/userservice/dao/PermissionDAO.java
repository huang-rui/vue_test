package com.syfri.userservice.dao;

import com.syfri.baseapi.dao.BaseDAO;
import com.syfri.userservice.model.PermissionVO;
import com.syfri.userservice.model.ResourceVO;

import java.util.List;

public interface PermissionDAO extends BaseDAO<PermissionVO>{

	/*--根据资源列表获取权限列表.--*/
	List<PermissionVO> doFindPermissionByResourceList(List<ResourceVO> resourceList);
}