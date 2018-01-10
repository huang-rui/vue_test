package com.syfri.userservice.model;

import java.io.Serializable;
import java.util.Date;

import com.syfri.baseapi.model.ValueObject;

public class AccountRoleVO extends ValueObject implements Serializable{

	private static final long serialVersionUID = 1L;

	private String userid;	//用户ID
	private String roleid;	//角色ID
	private String createId;	//创建人ID
	private String createName;	//创建人
	private Date createTime;	//创建时间
	private String alterId;	//修改人ID
	private String alterName;	//修改人
	private Date alterTime;	//修改时间
	private String reserve1;	//备用1
	private String reserve2;	//备用2
	private String reserve3;	//备用3
	private String deleteFlag;	//删除标志
	private String pkid;	//主键

	public String getUserid(){
		return userid;
	}
	public void setUserid(String userid){
		this.userid = userid;
	}
	public String getRoleid(){
		return roleid;
	}
	public void setRoleid(String roleid){
		this.roleid = roleid;
	}
	public String getCreateId(){
		return createId;
	}
	public void setCreateId(String createId){
		this.createId = createId;
	}
	public String getCreateName(){
		return createName;
	}
	public void setCreateName(String createName){
		this.createName = createName;
	}
	public Date getCreateTime(){
		return createTime;
	}
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	public String getAlterId(){
		return alterId;
	}
	public void setAlterId(String alterId){
		this.alterId = alterId;
	}
	public String getAlterName(){
		return alterName;
	}
	public void setAlterName(String alterName){
		this.alterName = alterName;
	}
	public Date getAlterTime(){
		return alterTime;
	}
	public void setAlterTime(Date alterTime){
		this.alterTime = alterTime;
	}
	public String getReserve1(){
		return reserve1;
	}
	public void setReserve1(String reserve1){
		this.reserve1 = reserve1;
	}
	public String getReserve2(){
		return reserve2;
	}
	public void setReserve2(String reserve2){
		this.reserve2 = reserve2;
	}
	public String getReserve3(){
		return reserve3;
	}
	public void setReserve3(String reserve3){
		this.reserve3 = reserve3;
	}
	public String getDeleteFlag(){
		return deleteFlag;
	}
	public void setDeleteFlag(String deleteFlag){
		this.deleteFlag = deleteFlag;
	}
	public String getPkid(){
		return pkid;
	}
	public void setPkid(String pkid){
		this.pkid = pkid;
	}
}