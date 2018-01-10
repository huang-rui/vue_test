package com.syfri.userservice.service;

import com.syfri.baseapi.service.BaseService;
import com.syfri.userservice.model.AccountVO;
import com.syfri.userservice.model.RoleVO;

import java.util.List;

public interface AccountService  extends BaseService<AccountVO>{

	/*采用MD5对密码进行加密.*/
	AccountVO getPasswordEncry(AccountVO accountVO);

	/*新增：向账户表中增加账户.*/
	int doInsertAccountByVO(AccountVO accountVO);

	/*修改：修改账户表.*/
	int doUpdateAccountByVO(AccountVO accountVO);

	/*--批量向账户角色中间表中插入数据(中间表).--*/
	int doInsertAccountRolesBatch(String userid, List<RoleVO> roles);

	/*--删除：删除账户同时删除其角色(中间表).--*/
	int doDeleteAccountRoles(String pkid);
}