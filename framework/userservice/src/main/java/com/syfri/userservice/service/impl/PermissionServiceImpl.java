package com.syfri.userservice.service.impl;

import com.syfri.userservice.model.ResourceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.syfri.baseapi.service.impl.BaseServiceImpl;
import com.syfri.userservice.dao.PermissionDAO;
import com.syfri.userservice.model.PermissionVO;
import com.syfri.userservice.service.PermissionService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
@Service("permissionService")
@Scope(proxyMode = ScopedProxyMode.INTERFACES)
public class PermissionServiceImpl extends BaseServiceImpl<PermissionVO> implements PermissionService {

	@Autowired
	private PermissionDAO permissionDAO;

	@Override
	public PermissionDAO getBaseDAO() {
		return permissionDAO;
	}

	/*--根据资源列表获取权限列表.--*/
	@Override
	public List<PermissionVO> doFindPermissionByResourceList(List<ResourceVO> resourceList){
		return permissionDAO.doFindPermissionByResourceList(resourceList);
	}
}