package com.syfri.userservice.controller;

import com.syfri.baseapi.model.ResultVO;
import com.syfri.baseapi.utils.EConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import com.syfri.userservice.model.RoleVO;
import com.syfri.userservice.service.RoleService;
import com.syfri.baseapi.controller.BaseController;

@Api(value = "角色管理",tags = "角色管理API",description = "角色管理")
@RestController
@RequestMapping("role")
public class RoleController  extends BaseController<RoleVO>{

	@Autowired
	private RoleService roleService;

	@Override
	public RoleService getBaseService() {
		return this.roleService;
	}

	/**
	 * 根据角色获取资源信息
	 */
	@ApiOperation(value="根据角色查询角色及其资源信息",notes="列表信息")
	@ApiImplicitParam(name="vo",value="角色对象")
	@RequiresPermissions("role:list")
	@GetMapping("/listByRole")
	public @ResponseBody ResultVO listByRole(RoleVO roleVO){
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(roleService.doFindRoleResources(roleVO));
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

	/**
	 * 新增角色，同时新增其资源信息
	 */
	@ApiOperation(value="根据角色新增角色及其资源信息",notes="新增")
	@ApiImplicitParam(name="vo",value="角色对象")
	@RequiresPermissions("role:add")
	@PostMapping("/insertByRole")
	public @ResponseBody ResultVO insertByRole(RoleVO roleVO){
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(roleService.doInsertRoleResources(roleVO));
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

	/**
	 * 修改角色，同时修改其资源信息
	 */
	@ApiOperation(value="根据角色修改角色及其资源信息",notes="修改")
	@ApiImplicitParam(name="vo",value="角色对象")
	@RequiresPermissions("role:update")
	@PostMapping("/updateByRole")
	public @ResponseBody ResultVO updateByRole(RoleVO roleVO){
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(roleService.doUpdateRoleResources(roleVO));
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

	/**
	 * 删除角色，同时删除其资源信息
	 */
	@ApiOperation(value="根据主键删除角色角色及其资源信息",notes="删除")
	@ApiImplicitParam(name="id",value="角色主键")
	@RequiresPermissions("role:delete")
	@PostMapping("/deleteById")
	public @ResponseBody ResultVO deleteById(String roleid){
		ResultVO resultVO = ResultVO.build();
		try{
			roleService.doDeleteRole(roleid);
			resultVO.setMsg("删除成功");
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}
}
