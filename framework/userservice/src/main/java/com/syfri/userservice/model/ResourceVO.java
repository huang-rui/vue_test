package com.syfri.userservice.model;

import java.io.Serializable;
import java.util.List;

import com.syfri.baseapi.model.ValueObject;

public class ResourceVO extends ValueObject implements Serializable{

	private static final long serialVersionUID = 1L;

	private String resourceid;	//资源ID（主键）
	private String resourcename;	//资源名称
	private String resourceinfo;	//资源描述
	private String parentid;	//父节点
	private Integer seqno;	//顺序
	private String icon;	//图标
	private String type;	//资源类型
	private String deleteFlag;	//删除标志
	private String createId;	//创建人ID
	private String createName;	//创建人
	private String createTime;	//创建时间
	private String alterId;	//修改人ID
	private String alterName;	//修改人
	private String alterTime;	//修改时间
	private String reserve1;	//备用1
	private String reserve2;	//备用2
	private String reserve3;	//备用3
	private List<PermissionVO> permissions;  //资源权限

	public String getResourceid(){
		return resourceid;
	}
	public void setResourceid(String resourceid){
		this.resourceid = resourceid;
	}
	public String getResourcename(){
		return resourcename;
	}
	public void setResourcename(String resourcename){
		this.resourcename = resourcename;
	}
	public String getResourceinfo(){
		return resourceinfo;
	}
	public void setResourceinfo(String resourceinfo){
		this.resourceinfo = resourceinfo;
	}
	public String getParentid(){
		return parentid;
	}
	public void setParentid(String parentid){
		this.parentid = parentid;
	}
	public Integer getSeqno(){
		return seqno;
	}
	public void setSeqno(Integer seqno){
		this.seqno = seqno;
	}
	public String getIcon(){
		return icon;
	}
	public void setIcon(String icon){
		this.icon = icon;
	}
	public String getType(){
		return type;
	}
	public void setType(String type){
		this.type = type;
	}
	public String getDeleteFlag(){
		return deleteFlag;
	}
	public void setDeleteFlag(String deleteFlag){
		this.deleteFlag = deleteFlag;
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
	public String getCreateTime(){
		return createTime;
	}
	public void setCreateTime(String createTime){
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
	public String getAlterTime(){
		return alterTime;
	}
	public void setAlterTime(String alterTime){
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

	public List<PermissionVO> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<PermissionVO> permissions) {
		this.permissions = permissions;
	}
}