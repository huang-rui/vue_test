package com.syfri.userservice.controller;

import com.syfri.baseapi.controller.BaseController;
import com.syfri.userservice.model.*;
import com.syfri.userservice.service.*;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Api(value = "测试",tags = "测试API",description = "测试")
@RestController
@RequestMapping("/test")
public class TestController extends BaseController<UserVO>{

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private PermissionService permissionService;

	@Autowired
	private ResourceService resourceService;

	@Override
	public UserService getBaseService() {
		return this.userService;
	}

	//1
	@RequestMapping("/count")
	public int count(){
		UserVO vo = new UserVO();
		return userService.doSearchCount(vo);
	}

	//2

	@RequestMapping("/findById")
	public UserVO findById(){
		return userService.doFindById("1");
	}

	//3
//	@RequestMapping("/findByVO")
//	public List<UserVO> findByVO(){
//		UserVO vo = new UserVO();
//		vo.setUsername("aa");
//		return userService.doSearchListByVO(vo);
//	}

	//4
//	@RequestMapping("/insertByVO")
//	public int insertByVO(){
//		UserVO vo = new UserVO();
//		vo.setUsername("zzz");
//		vo.setPassword("1");
//		return userService.doInsertByVO(vo);
//	}

	//5
//	@RequestMapping("/insertByList")
//	public int insertByList(){
//		List<UserVO> list = new ArrayList<UserVO>();
//		UserVO vo1 = new UserVO();
//		vo1.setUsername("aa");
//		vo1.setPassword("1");
//		list.add(vo1);
//		UserVO vo2 = new UserVO();
//		vo2.setUsername("bb");
//		vo2.setPassword("1");
//		list.add(vo2);
//		return userService.doBatchInsertByList(list);
//	}

	//6
	@RequestMapping("/deleteById")
	public int insertByMap(){
		return userService.doDeleteById("11111");
	}

	//7
//	@RequestMapping("updateByVO")
//	public int updateByVO(){
//		UserVO vo = userService.doFindById("1");
//		vo = userService.getPasswordEncry(vo);
//		UserVO vo2 = userService.doFindById("2");
//		vo2 = userService.getPasswordEncry(vo2);
//		userService.doUpdateByVO(vo2);
//		return userService.doUpdateByVO(vo);
//	}

	//8
//	@RequestMapping("/updateByList")
//	public int updateByList(){
//		List<UserVO> list = new ArrayList<UserVO>();
//		UserVO vo1 = new UserVO();
//		vo1.setPkid("2");
//		vo1.setUsername("lucy22");
//		vo1.setPassword("1");
//		list.add(vo1);
//		UserVO vo2 = new UserVO();
//		vo2.setPkid("3");
//		vo2.setUsername("smith33");
//		vo2.setPassword("1");
//		list.add(vo2);
//		return userService.doBatchUpdateByList(list);
//	}
	//9
//	@RequestMapping("doFindByVO")
//	public UserVO doFindByVO(){
//		UserVO userVO = new UserVO();
//		userVO.setUsername("aaa");
//		return userService.doFindByVO(userVO);
//	}
	//10
	@RequestMapping("getRoleByUserid")
	public List<RoleVO> getUserRoleByVO(){
		return roleService.doFindRoleByUserid("1");
	}

	//11
	@RequestMapping("getUsers")
	@RequiresPermissions("user:list")
	public List<UserVO> getUsers(){
		UserVO vo = new UserVO();
		AccountVO accountVO = new AccountVO();
		accountVO.setUsername("lucy222");
		vo.setAccountVO(accountVO);
		List<UserVO> lists = userService.doFindUserRoles(vo);
		return lists;
	}

	//12
	@RequestMapping("insertUser")
	@RequiresPermissions("user:add")
	public UserVO insertUser(){
		UserVO vo = new UserVO();
		AccountVO accountVO = new AccountVO();
		accountVO.setUsername("aaaa");
		accountVO.setPassword("1");
		vo.setAccountVO(accountVO);
		vo.setRealname("aaaa222aaaa");
		List<RoleVO> roles = new ArrayList<RoleVO>();
		RoleVO role = new RoleVO();
		role.setRolename("normal");
		role.setRoleid("2");
		roles.add(role);
		vo.setRoles(roles);
		return userService.doInsertUserRoles(vo);
	}

	//13
	@RequestMapping("updateUser")
	@RequiresPermissions("user:update")
	public UserVO updateUser(){
		UserVO user = new UserVO();
		user.setPkid("3");
		UserVO vo = userService.doFindUserRoles(user).get(0);
		vo.setRealname("20180103");
		List<RoleVO> roles = new ArrayList<>();
		RoleVO role = new RoleVO();
		role.setRolename("normal");
		role.setRoleid("2");
		roles.add(role);
		vo.setRoles(roles);
		return userService.doUpdateUserRoles(vo);
	}

	//14
	@RequestMapping("deleteUser")
	@RequiresPermissions("user:delete")
	public void deleteUser(){
		userService.doDeleteUserRoles("3");
	}

	//15
	@RequestMapping("getRoles")
	@RequiresPermissions("role:list")
	public List<RoleVO> getRoles(){
		RoleVO vo = new RoleVO();
		vo.setRolename("admin");
		List<RoleVO> lists = roleService.doFindRoleResources(vo);
		return lists;
	}

	//16
	@RequestMapping("insertRole")
	@RequiresPermissions("role:add")
	public RoleVO insertRole(){
		RoleVO vo = new RoleVO();
		vo.setRolename("角色3");

		List<ResourceVO> list = new ArrayList<>();
		ResourceVO rp = new ResourceVO();
		rp.setResourceid("1");
		list.add(rp);
		ResourceVO rp2 = new ResourceVO();
		rp2.setResourceid("2");
		list.add(rp2);
		vo.setResources(list);

		return roleService.doInsertRoleResources(vo);
	}

	//17
	@RequestMapping("updateRole")
	@RequiresPermissions("role:update")
	public RoleVO updateRole() throws Exception {
		RoleVO vo = new RoleVO();
		vo.setRoleid("A1AEACA6D7DD4131A8D7F9ADF1A5DAB0");
		vo.setRoleinfo("111weadsadadewq1");

		List<ResourceVO> list = new ArrayList<>();
		ResourceVO p1 = new ResourceVO();
		p1.setResourceid("1");
		list.add(p1);
		ResourceVO p2 = new ResourceVO();
		p2.setResourceid("2");
		p2.setReserve1(null);
		list.add(p2);
		vo.setResources(list);

		return roleService.doUpdateRoleResources(vo);
	}

	//18
	@RequestMapping("deleteRole")
	@RequiresPermissions("role:delete")
	public void deleteRole(){
		roleService.doDeleteRole("A1AEACA6D7DD4131A8D7F9ADF1A5DAB0");
	}

	//19
	@RequestMapping("getPermission")
	@RequiresPermissions("permission:list")
	public List<PermissionVO> getPermission(){
		PermissionVO vo = new PermissionVO();
		vo.setPermissionid("1");
		return permissionService.doSearchListByVO(vo);
	}

	//20
	@RequestMapping("getPermissions")
	@RequiresPermissions("permission:list")
	public List<PermissionVO> getPermissions(){
		return permissionService.doSearchListByVO(null);
	}

	//21
	@RequestMapping("insertPermission")
	@RequiresPermissions("permission:add")
	public int insertPermission(){
		PermissionVO vo = new PermissionVO();
		vo.setPermissionname("test");
		return permissionService.doInsertByVO(vo);
	}

	//22
	@RequestMapping("updatePermission")
	@RequiresPermissions("permission:update")
	public int updatePermission(){
		PermissionVO vo = new PermissionVO();
		vo.setPermissioninfo("test");
		vo.setPermissionid("C642000235894C129742E44F2A7BEAE8");
		return permissionService.doUpdateByVO(vo);
	}

	//23
	@RequestMapping("deletePermission")
	@RequiresPermissions("permission:delete")
	public int deletePermission(){
		return permissionService.doDeleteById("2E85A37D74314CBDAFA0AC0148F41BA1");
	}

	//24
	@RequestMapping("getResource")
	@RequiresPermissions("resource:list")
	public List<ResourceVO> getResource(){
		ResourceVO vo = new ResourceVO();
		vo.setResourceid("2");
		return resourceService.doFindResourcePermissions(vo);
	}

	//25
	@RequestMapping("getResources")
	@RequiresPermissions("resource:list")
	public List<ResourceVO> getResources(){
		return resourceService.doSearchListByVO(null);
	}

	//26
	@RequestMapping("insertResource")
	@RequiresPermissions("resource:add")
	public ResourceVO insertResource(){
		ResourceVO vo = new ResourceVO();
		vo.setResourcename("/test/test");
		vo.setResourceinfo("权限管理");
		vo.setParentid("-1");
		vo.setSeqno(3);

		List<PermissionVO> permissions = new ArrayList<>();
		PermissionVO permission = new PermissionVO();
		permission.setPermissionid("1");
		permissions.add(permission);
		vo.setPermissions(permissions);

		return resourceService.doInsertResourcePermissions(vo);
	}

	//27
	@RequestMapping("updateResource")
	@RequiresPermissions("resource:update")
	public ResourceVO updateResource(){
		ResourceVO vo = new ResourceVO();
		vo.setResourceinfo("权限管理测试2");
		vo.setResourceid("5EB53C0711D343FA884992B3ABC68936");

		List<PermissionVO> permissions = new ArrayList<>();
		PermissionVO permission1 = new PermissionVO();
		permission1.setPermissionid("1");
		permissions.add(permission1);
		vo.setPermissions(permissions);

		PermissionVO permission2 = new PermissionVO();
		permission2.setPermissionid("3");
		permissions.add(permission2);

		return resourceService.doUpdateResourcePermissions(vo);
	}

	//28
	@RequestMapping("deleteResource")
	@RequiresPermissions("resource:delete")
	public void deleteResource(){
		resourceService.doDeleteResourcePermissions("5EB53C0711D343FA884992B3ABC68936");
	}

}
