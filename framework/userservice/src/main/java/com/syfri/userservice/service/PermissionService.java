package com.syfri.userservice.service;

import com.syfri.baseapi.service.BaseService;
import com.syfri.userservice.model.PermissionVO;
import com.syfri.userservice.model.ResourceVO;

import java.util.List;

public interface PermissionService  extends BaseService<PermissionVO>{

	/*--根据资源列表获取权限列表.--*/
	List<PermissionVO> doFindPermissionByResourceList(List<ResourceVO> resourceList);
}