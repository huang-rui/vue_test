package com.syfri.userserver.domain;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "sys_user")
public class SysUser implements Serializable{
	@Id
	@GenericGenerator(name= "idGenerator",strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	private String  userid;
	private String username;
	private String password;
	private String realname;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birth;
	private String sex;
	private String deptid;
	private String position;
	private String phone;
	private String mobile;
	private String email;
	@Column(name = "create_id")
	private Long createId;
	@Column(name = "create_name",length = 30)
	private String createName;
	@Column(name = "create_time")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	@Column(name = "alter_id")
	private Long alterId;
	@Column(name = "alter_name",length = 30)
	private String alterName;
	@Column(name = "alter_time")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date alterTime;

	@ManyToMany(cascade = {}, fetch = FetchType.EAGER)
	@JoinTable(name = "s_user_role",
			joinColumns = {@JoinColumn(name= "userid")},
			inverseJoinColumns = {@JoinColumn(name= "roleid")})
	private List<SysRole> roles;

	public SysUser(){
	}
	public String getUserid(){
		return userid;
	}
	public void setUserid(String userid){
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getDeptid() {
		return deptid;
	}

	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getCreateId() {
		return createId;
	}

	public void setCreateId(Long createId) {
		this.createId = createId;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getAlterId() {
		return alterId;
	}

	public void setAlterId(Long alterId) {
		this.alterId = alterId;
	}

	public String getAlterName() {
		return alterName;
	}

	public void setAlterName(String alterName) {
		this.alterName = alterName;
	}

	public Date getAlterTime() {
		return alterTime;
	}

	public void setAlterTime(Date alterTime) {
		this.alterTime = alterTime;
	}

	public List<SysRole> getRoles() {
		return roles;
	}

	public void setRoles(List<SysRole> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "SysUser{" +
				"userid='" + userid + '\'' +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", realname='" + realname + '\'' +
				", birth=" + birth +
				", sex='" + sex + '\'' +
				", deptid='" + deptid + '\'' +
				", position='" + position + '\'' +
				", phone='" + phone + '\'' +
				", mobile='" + mobile + '\'' +
				", email='" + email + '\'' +
				", createId=" + createId +
				", createName='" + createName + '\'' +
				", createTime=" + createTime +
				", alterId=" + alterId +
				", alterName='" + alterName + '\'' +
				", alterTime=" + alterTime +
				", roles=" + roles +
				'}';
	}
}
