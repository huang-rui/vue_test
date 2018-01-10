package com.syfri.userservice.controller;

import com.syfri.baseapi.model.ResultVO;
import com.syfri.baseapi.utils.EConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import com.syfri.userservice.model.UserVO;
import com.syfri.userservice.service.UserService;
import com.syfri.baseapi.controller.BaseController;

@Api(value = "用户管理",tags = "用户管理API",description = "用户管理")
@RestController
@RequestMapping("user")
public class UserController  extends BaseController<UserVO>{

	private static final Logger logger  = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@Override
	public UserService getBaseService() {
		return this.userService;
	}

	@RequestMapping("/list")
	@RequiresPermissions("user:list")
	public String userList(){
		return "userList";
	}

	@RequestMapping("/add")
	@RequiresPermissions("user:add")
	public String userAdd(){
		return "userAdd";
	}

	/**
	 * 根据用户获取用户信息及角色信息
	 */
	@ApiOperation(value="根据用户查询用户信息及角色信息",notes="列表信息")
	@ApiImplicitParam(name="vo",value="用户对象")
	@RequiresPermissions("user:list")
	@GetMapping("/listByUser")
	public @ResponseBody ResultVO listByUser(UserVO userVO){
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(userService.doFindUserRoles(userVO));
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

	/**
	 * 新增用户，同时新增账号及用户角色
	 */
	@ApiOperation(value="根据用户新增用户（包括账户和角色）",notes="新增")
	@ApiImplicitParam(name="vo",value="用户对象")
	@RequiresPermissions("user:add")
	@PostMapping("/insertByUser")
	public @ResponseBody ResultVO insertByUser(UserVO userVO){
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(userService.doInsertUserRoles(userVO));
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

	/**
	 * 修改用户，同时修改账号及用户角色
	 */
	@ApiOperation(value="根据用户修改用户（包括账户和角色）",notes="修改")
	@ApiImplicitParam(name="vo",value="用户对象")
	@RequiresPermissions("user:update")
	@PostMapping("/updateByUser")
	public @ResponseBody ResultVO updateByUser(UserVO userVO){
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(userService.doUpdateUserRoles(userVO));
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

	/**
	 * 删除用户，同时删除账号及用户角色
	 */
	@ApiOperation(value="根据主键删除用户（包括账户和角色）",notes="删除")
	@ApiImplicitParam(name="id",value="用户主键")
	@RequiresPermissions("user:delete")
	@PostMapping("/deleteById")
	public @ResponseBody ResultVO deleteById(String pkid){
		ResultVO resultVO = ResultVO.build();
		try{
			userService.doDeleteUserRoles(pkid);
			resultVO.setMsg("删除成功");
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

}
