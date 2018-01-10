package com.syfri.baseapi.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.syfri.baseapi.dao.BaseDAO;
import com.syfri.baseapi.model.ValueObject;
import com.syfri.baseapi.service.BaseService;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

public abstract class BaseServiceImpl<T extends ValueObject> implements BaseService<T>{

	public abstract BaseDAO<T> getBaseDAO();

	@Override
	public int doSearchCount(T vo) {
		return getBaseDAO().doSearchCount(vo);
	}

	@Override
	public int doInsertByVO(T vo) {
		return getBaseDAO().doInsertByVO(vo);
	}

	@Override
	public int doBatchInsertByList(List<T> list) {
		return getBaseDAO().doBatchInsertByList(list);
	}

	@Override
	public int doInsertByMap(Map<String,Object> param) {
		return getBaseDAO().doInsertByMap(param);
	}

	@Override
	public int doDeleteById(String id) {
		return getBaseDAO().doDeleteById(id);
	}

	@Override
	public int doDeleteByVO(T vo) {
		return getBaseDAO().doDeleteByVO(vo);
	}

	@Override
	public int doDeleteByMap(Map<String,Object> param) {
		return getBaseDAO().doDeleteByMap(param);
	}

	@Override
	public int doUpdateByVO(T vo) {
		return getBaseDAO().doUpdateByVO(vo);
	}

	@Override
	public int doBatchUpdateByList(List<T> list) {
		return getBaseDAO().doBatchUpdateByList(list);
	}

	@Override
	public int doUpdateByMap(Map<String,Object> param) {
		return getBaseDAO().doUpdateByMap(param);
	}

	@Override
	public T doFindById(String id) {
		return getBaseDAO().doFindById(id);
	}

	@Override
	public T doFindByVO(T vo) {
		return getBaseDAO().doFindByVO(vo);
	}

	@Override
	public List doSearchListByVO(T vo) {
		return getBaseDAO().doSearchListByVO(vo);
	}

	@Override
	public List doSearchListByMap(Map<String,Object> param) {
		return getBaseDAO().doSearchListByMap(param);
	}

	@Override
	public PageInfo<T> doSearchPage(T vo) {
		String orderStr = "";
		if(!StringUtils.isEmpty(vo.getOrderName())){
			orderStr = vo.getOrderName()+" "+vo.getOrderRule();
		}
		return this.doSearchPage(vo, orderStr, vo.getPageNum(), vo.getPageSize());
	}

	@Override
	public PageInfo doSearchPage(T vo, int pageNum, int pageSize) {
		String orderStr = "";
		if(!StringUtils.isEmpty(vo.getOrderName())){
			orderStr = vo.getOrderName()+" "+vo.getOrderRule();
		}
		return this.doSearchPage(vo, orderStr, pageNum, pageSize);
	}

	@Override
	public PageInfo doSearchPage(T vo, String orderStr, int pageNum, int pageSize) {
		if(!"".equals(orderStr.trim())){
			PageHelper.startPage(pageNum, pageSize, orderStr);
		}else{
			PageHelper.startPage(pageNum, pageSize);
		}
		List<T> list = this.getBaseDAO().doSearchListByVO(vo);
		PageInfo<T> page = new PageInfo<T>(list);
		return page;
	}

	@Override
	public PageInfo doSearchPage(Map<String,Object> map, int pageNum, int pageSize) {
		return this.doSearchPage(map, "", pageNum, pageSize);
	}

	@Override
	public PageInfo doSearchPage(Map<String,Object> map, String orderStr, int pageNum, int pageSize) {
		if(!"".equals(orderStr.trim())){
			PageHelper.startPage(pageNum, pageSize,orderStr);
		}else{
			PageHelper.startPage(pageNum, pageSize);
		}
		List<T> list = this.getBaseDAO().doSearchListByMap(map);
		PageInfo<T> page = new PageInfo<T>(list);
		return page;
	}
}
