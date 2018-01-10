package com.syfri.userservice.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.syfri.userservice.model.AccountVO;
import com.syfri.userservice.service.AccountService;
import com.syfri.baseapi.controller.BaseController;

@Api(value = "账户管理",tags = "账户管理API",description = "账户管理")
@RestController
@RequestMapping("account")
public class AccountController  extends BaseController<AccountVO>{

	@Autowired
	private AccountService accountService;

	@Override
	public AccountService getBaseService() {
		return this.accountService;
	}

}
