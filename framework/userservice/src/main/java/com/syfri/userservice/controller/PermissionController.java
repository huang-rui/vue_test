package com.syfri.userservice.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.syfri.userservice.model.PermissionVO;
import com.syfri.userservice.service.PermissionService;
import com.syfri.baseapi.controller.BaseController;

@Api(value = "权限管理",tags = "权限管理API",description = "权限管理")
@RestController
@RequestMapping("permission")
public class PermissionController  extends BaseController<PermissionVO>{

	@Autowired
	private PermissionService permissionService;

	@Override
	public PermissionService getBaseService() {
		return this.permissionService;
	}

}
