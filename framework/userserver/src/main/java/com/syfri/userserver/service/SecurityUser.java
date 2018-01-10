package com.syfri.userserver.service;

import com.syfri.userserver.domain.SysRole;
import com.syfri.userserver.domain.SysUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SecurityUser extends SysUser implements UserDetails{
	private static final long serialVersionUID =1L;
	public SecurityUser(SysUser user){
		if(user != null){
			this.setUserid(user.getUserid());
			this.setUsername(user.getUsername());
			this.setPassword(user.getPassword());
			this.setRealname(user.getRealname());
			this.setBirth(user.getBirth());
			this.setSex(user.getSex());
			this.setDeptid(user.getDeptid());
			this.setPosition(user.getPosition());
			this.setEmail(user.getEmail());
			this.setMobile(user.getMobile());
			this.setPhone(user.getPhone());
			this.setCreateId(user.getCreateId());
			this.setCreateName(user.getCreateName());
			this.setCreateTime(user.getCreateTime());
			this.setAlterId(user.getAlterId());
			this.setAlterName(user.getUsername());
			this.setAlterTime(user.getAlterTime());
		}
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		List<SysRole> roles = this.getRoles();
		if(roles != null)
		{
			for (SysRole role : roles) {
				SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getRolename());
				authorities.add(authority);
			}
		}
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String getPassword() {
		return super.getPassword();
	}

	@Override
	public String getUsername() {
		return super.getUsername();
	}
}
