package com.syfri.userservice.service.impl;

import com.syfri.userservice.model.AccountVO;
import com.syfri.userservice.service.AccountService;
import com.syfri.userservice.utils.CurrentUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.syfri.baseapi.service.impl.BaseServiceImpl;
import com.syfri.userservice.dao.UserDAO;
import com.syfri.userservice.model.UserVO;
import com.syfri.userservice.service.UserService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<UserVO> implements UserService {

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private AccountService accountService;

	@Override
	public UserDAO getBaseDAO() {
		return userDAO;
	}

	/*--查询：根据用户获取用户及其角色--.*/
	@Override
	public List<UserVO> doFindUserRoles(UserVO userVO) {
		return userDAO.doFindUserRoles(userVO);
	}

	/*--新增：增加用户并为用户赋予角色.--*/
	@Override
	public UserVO doInsertUserRoles(UserVO userVO){

		//向账户表SYS_ACCOUNT插入账户信息
		AccountVO accountVO = userVO.getAccountVO();
		accountService.doInsertAccountByVO(accountVO);

		//向用户表SYS_USER插入用户信息
		userVO.setCreateId(CurrentUserUtil.getCurrentUserId());
		userVO.setCreateName(CurrentUserUtil.getCurrentUserName());
		userVO.setUserid(accountVO.getUserid());
		userDAO.doInsertByVO(userVO);

		//向中间表中插入账户角色情况
		accountService.doInsertAccountRolesBatch(accountVO.getUserid(), userVO.getRoles());
		return userVO;
	}

	/*--修改：修改用户并修改用户角色.--*/
	@Override
	public UserVO doUpdateUserRoles(UserVO userVO){
		//修改账户表
		AccountVO accountVO = userVO.getAccountVO();
		accountService.doUpdateAccountByVO(accountVO);

		//修改用户表基本信息
		userDAO.doUpdateByVO(userVO);

		//修改角色中间表信息
		accountService.doDeleteAccountRoles(userVO.getUserid());
		accountService.doInsertAccountRolesBatch(accountVO.getUserid(), userVO.getRoles());
		return userVO;
	}

	/*--删除：删除用户同时删除其角色.--*/
	@Override
	public void doDeleteUserRoles(String pkid){
		String userid = userDAO.doFindById(pkid).getUserid();
		//删除用户表
		userDAO.doDeleteById(pkid);
		//删除账户表
		accountService.doDeleteById(userid);
		//删除账户角色中间表
		accountService.doDeleteAccountRoles(userid);
	}

}