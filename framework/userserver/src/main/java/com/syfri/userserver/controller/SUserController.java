//package com.syfri.userservice.controller;
//
//import com.syfri.userservice.entity.SysRole;
//import com.syfri.userservice.entity.SysUser;
//import com.syfri.userservice.repository.SysRoleRepository;
//import com.syfri.userservice.repository.SUserRepository;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.util.StringUtils;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import java.security.Principal;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//@Controller
//@RequestMapping("/user")
//public class SUserController {
//	private static Logger logger = LoggerFactory.getLogger(SUserController.class);
//
//	@Autowired
//	private SUserRepository sUserRepository;
//	@Autowired
//	private SysRoleRepository sRoleRepository;
//
//	@Value("${securityconfig.urlroles}")
//	private String urlroles;
//
//	@RequestMapping("/save")
//	SysUser save(){
//		//先删除S_USER和S_ROLE表中的数据
//		sUserRepository.deleteAll();
//		sRoleRepository.deleteAll();
//		//创建一个角色并保存
//		SysRole role = new SysRole();
//		role.setRolename("admin");
//		sRoleRepository.save(role);
//		//创建一个用户并保存
//		SysUser user = new SysUser();
//		user.setUsername("admin");
//		BCryptPasswordEncoder bpe = new BCryptPasswordEncoder();
//		user.setPassword(bpe.encode("1"));
//		user.setCreateTime(new Date());
//		List<SysRole> roles = sRoleRepository.findAll();
//		user.setRoles(roles);
//		sUserRepository.save(user);
//		return user;
//	}
//
//	@RequestMapping("/index")
//	public String index(ModelMap model, Principal user){
//		Authentication authentication = (Authentication) user;
//		List<String> userroles = new ArrayList<>();
//		for(GrantedAuthority ga : authentication.getAuthorities()){
//			userroles.add(ga.getAuthority());
//		}
//
//		Boolean newrole = false;
//		Boolean editrole = false;
//		Boolean deleterole = false;
//		if(StringUtils.isEmpty(urlroles)){
//			String[] resources = urlroles.split(";");
//			for(String resource : resources){
//				String[] urls = resource.split("=");
//				if(urls[0].indexOf("new")>0){
//					String[] newroles = urls[1].split(",");
//					for(String str : newroles){
//						if(urlroles.contains(str.trim())){
//							newrole = true;
//							break;
//						}
//					}
//				}else if(urls[0].indexOf("edit") > 0){
//					String[] editoles = urls[1].split(",");
//					for(String str : editoles){
//						str = str.trim();
//						if(userroles.contains(str)){
//							editrole = true;
//							break;
//						}
//					}
//				}else if(urls[0].indexOf("delete") > 0){
//					String[] deleteroles = urls[1].split(",");
//					for(String str : deleteroles){
//						str = str.trim();
//						if(userroles.contains(str)){
//							deleterole = true;
//							break;
//						}
//					}
//				}
//			}
//		}
//		model.addAttribute("newrole",newrole);
//		model.addAttribute("editrole",editrole);
//		model.addAttribute("deleterole",deleterole);
//
//		model.addAttribute("user",user);
//		return "user/index";
//	}
//}
