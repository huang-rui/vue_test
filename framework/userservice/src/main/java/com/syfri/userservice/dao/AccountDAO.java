package com.syfri.userservice.dao;

import com.syfri.baseapi.dao.BaseDAO;
import com.syfri.userservice.model.AccountRoleVO;
import com.syfri.userservice.model.AccountVO;

import java.util.List;

public interface AccountDAO extends BaseDAO<AccountVO>{

	/*新增账户时批量插入账户角色.*/
	int doBatchInsertAccountRoles(List<AccountRoleVO> accountRoles);

	/*修改用户时删除用户中间表数据*/
	int doDeleteAccountRoles(String userid);
}