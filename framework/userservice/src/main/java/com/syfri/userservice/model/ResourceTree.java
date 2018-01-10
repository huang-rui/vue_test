package com.syfri.userservice.model;

import java.io.Serializable;
import java.util.List;

public class ResourceTree implements Serializable{

	private static final Long serialVersionUID = 1L;

	/*主键.*/
	private String resourceid;

	/*资源名称.*/
	private String resourcename;

	/*资源描述.*/
	private String resourceinfo;

	/*顺序.*/
	private Integer seqno;

	/*图标.*/
	private String icon;

	/*资源类型.*/
	private String type;

	/*子资源.*/
	private List<ResourceTree> children;

	public ResourceTree() {
	}

	public ResourceTree(String resourceid, String resourcename, String resourceinfo, Integer seqno, String icon, String type) {
		this.resourceid = resourceid;
		this.resourcename = resourcename;
		this.resourceinfo = resourceinfo;
		this.seqno = seqno;
		this.icon = icon;
		this.type = type;
	}

	public String getResourceid() {
		return resourceid;
	}

	public void setResourceid(String resourceid) {
		this.resourceid = resourceid;
	}

	public String getResourcename() {
		return resourcename;
	}

	public void setResourcename(String resourcename) {
		this.resourcename = resourcename;
	}

	public String getResourceinfo() {
		return resourceinfo;
	}

	public void setResourceinfo(String resourceinfo) {
		this.resourceinfo = resourceinfo;
	}

	public Integer getSeqno() {
		return seqno;
	}

	public void setSeqno(Integer seqno) {
		this.seqno = seqno;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<ResourceTree> getChildren() {
		return children;
	}

	public void setChildren(List<ResourceTree> children) {
		this.children = children;
	}
}
