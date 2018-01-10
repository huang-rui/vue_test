package com.syfri.userservice.controller;

import com.syfri.baseapi.model.ResultVO;
import com.syfri.baseapi.utils.EConstants;
import com.syfri.userservice.model.RoleVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import com.syfri.userservice.model.ResourceVO;
import com.syfri.userservice.service.ResourceService;
import com.syfri.baseapi.controller.BaseController;

import java.util.List;

@Api(value = "资源管理",tags = "资源管理API",description = "资源管理")
@RestController
@RequestMapping("resource")
public class ResourceController  extends BaseController<ResourceVO>{

	@Autowired
	private ResourceService resourceService;

	@Override
	public ResourceService getBaseService() {
		return this.resourceService;
	}

	/**
	 * 根据资源获取权限信息
	 */
	@ApiOperation(value="根据资源查询资源及其权限信息",notes="列表信息")
	@ApiImplicitParam(name="vo",value="资源对象")
	@RequiresPermissions("resource:list")
	@GetMapping("/listByResource")
	public @ResponseBody ResultVO listByResource(ResourceVO resourceVO){
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(resourceService.doFindResourcePermissions(resourceVO));
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

	/**
	 * 新增资源，同时新增其权限信息
	 */
	@ApiOperation(value="根据资源新增资源及其权限信息",notes="新增")
	@ApiImplicitParam(name="vo",value="资源对象")
	@RequiresPermissions("resource:add")
	@PostMapping("/insertByResource")
	public @ResponseBody ResultVO insertByResource(ResourceVO resourceVO){
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(resourceService.doInsertResourcePermissions(resourceVO));
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

	/**
	 * 修改资源，同时修改其权限信息
	 */
	@ApiOperation(value="根据资源修改资源及其权限信息",notes="修改")
	@ApiImplicitParam(name="vo",value="资源对象")
	@RequiresPermissions("resource:update")
	@PostMapping("/updateByResource")
	public @ResponseBody ResultVO updateByResource(ResourceVO resourceVO){
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(resourceService.doUpdateResourcePermissions(resourceVO));
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

	/**
	 * 删除资源，同时删除其权限信息
	 */
	@ApiOperation(value="根据主键删除资源资源及其权限信息",notes="删除")
	@ApiImplicitParam(name="id",value="资源主键")
	@RequiresPermissions("resource:delete")
	@PostMapping("/deleteById")
	public @ResponseBody ResultVO deleteById(String resourceid){
		ResultVO resultVO = ResultVO.build();
		try{
			resourceService.doDeleteResourcePermissions(resourceid);
			resultVO.setMsg("删除成功");
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

	/**
	 * 根据角色获取资源树
	 */
	@ApiOperation(value="根据角色获取资源树",notes="列表信息")
	@ApiImplicitParam(name="vo",value="资源对象")
	@RequiresPermissions("resource:list")
	@GetMapping("/getResourceTree")
	public @ResponseBody ResultVO getResourceTree(List<RoleVO> roleList){
		ResultVO resultVO = ResultVO.build();
		try{
			resultVO.setResult(resourceService.getResourceTree(roleList));
		}catch(Exception e){
			logger.error("{}",e.getMessage());
			resultVO.setCode(EConstants.CODE.FAILURE);
		}
		return resultVO;
	}

}
