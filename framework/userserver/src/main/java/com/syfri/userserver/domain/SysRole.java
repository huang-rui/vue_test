package com.syfri.userserver.domain;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "sys_role")
public class SysRole implements Serializable{
	@Id
	@GenericGenerator(name= "idGenerator",strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	private String roleid;
	private String rolename;
	private String roleinfo;
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

	public SysRole(){
	}
	public String getRoleid(){
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public String getRoleinfo() {
		return roleinfo;
	}

	public void setRoleinfo(String roleinfo) {
		this.roleinfo = roleinfo;
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
}
