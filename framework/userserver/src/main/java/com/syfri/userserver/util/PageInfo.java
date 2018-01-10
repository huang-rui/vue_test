package com.syfri.userserver.util;

import com.github.pagehelper.Page;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class PageInfo<T> implements Serializable {

	private static final Long serialVersionID = 1L;

	/*当前页.*/
	private int pageNum;

	/*每页的条数.*/
	private int pageSize;

	/*总记录数.*/
	private long total;

	/*总页数.*/
	private int pages;

	/*结果集.*/
	private List<T> list;

	/*是否为第一页.*/
	private boolean isFirstPage = false;

	/*是否为最后一页.*/
	private boolean isLastPage = false;

	public PageInfo(){}

	public PageInfo(List<T> list){
		if(list instanceof Page){
			Page page = (Page)list;
			this.pageNum = page.getPageNum();
			this.pageSize = page.getPageSize();
			this.pages = page.getPages();
			this.list = page;
			this.total = page.getTotal();
		}else if(list instanceof Collections){
			this.pageNum = 1;
			this.pageSize = list.size();
			this.pages =  1;
			this.list = list;
			this.total = list.size();
		}
		/*判断页面边界.*/
		if(list instanceof Collections){
			judgePageBoudary();
		}
	}

	private void judgePageBoudary(){
		isFirstPage = pageNum == 1;
		isLastPage = pageNum == pages;
	}

	public static Long getSerialVersionID() {
		return serialVersionID;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public boolean isFirstPage() {
		return isFirstPage;
	}

	public void setFirstPage(boolean firstPage) {
		isFirstPage = firstPage;
	}

	public boolean isLastPage() {
		return isLastPage;
	}

	public void setLastPage(boolean lastPage) {
		isLastPage = lastPage;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("PageInfo{");
		sb.append("pageNum=").append(pageNum);
		sb.append(", pageSize=").append(pageSize);
		sb.append(", total=").append(total);
		sb.append(", pages=").append(pages);
		sb.append(", list=").append(list);
		sb.append(", isFirstPage=").append(isFirstPage);
		sb.append(", isLastPage=").append(isLastPage);
		sb.append('}');
		return sb.toString();
	}
}

