package com.syfri.userserver.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.syfri.userserver.domain.Result;
import com.syfri.userserver.domain.SysUser;
import com.syfri.userserver.exception.UserException;
import com.syfri.userserver.mapper.UserEntityMapper;
import com.syfri.userserver.domain.UserEntity;
import com.syfri.userserver.repository.SysUserRepository;
import com.syfri.userserver.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@ResponseBody
public class MyBatisController {
	@Autowired
	private SysUserRepository sysUserRepository;

	@Autowired
	private UserEntityMapper userEntityMapper;

	@RequestMapping("/test")
	public List<SysUser> userList(){
		//return sUserRepository.findAll();
		return null;
	}
	@RequestMapping("/getAll")
	public List<UserEntity> getAll(){
		return userEntityMapper.getAllUsers();
	}
	@RequestMapping("/add")
	public int insertUser(@RequestParam("username") String username, @RequestParam("password") String password){
		return userEntityMapper.insertUser(username,password);
	}
	/*
	@getMapping(value ="/findOne/{id}")
	public UserEntity findOne(@PathVariable("userid") String userid){
		return userEntityMapper.findOne(userid);
	}
	*/

	@RequestMapping(value ="/findOne")
	public Result<UserEntity> findOne(){
		UserEntity user = userEntityMapper.findOne("11111");
		System.out.println(user.toString());
		String password = user.getPassword();
		if("1111".equals(password)){
			return ResultUtil.success(user);
		}else{
			throw new UserException(-1,"不知道什么错误");
		}
	}

	@RequestMapping(value = "findByPage")
	public Page<UserEntity> findByPage(){
		PageHelper.startPage(2,3);
		return userEntityMapper.findByPage();
	}
}
