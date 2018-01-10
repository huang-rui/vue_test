package com.syfri.userservice.dao;

import com.syfri.baseapi.dao.BaseDAO;
import com.syfri.userservice.model.AccountRoleVO;
import com.syfri.userservice.model.UserVO;

import java.util.List;

public interface UserDAO extends BaseDAO<UserVO>{

	/*获取用户，如果userVO为null，则获取所有用户.*/
	List<UserVO> doFindUserRoles(UserVO userVO);

}