package com.syfri.baseapi.dao;

import com.syfri.baseapi.model.ValueObject;

import java.util.List;
import java.util.Map;

public interface BaseDAO<T extends ValueObject> {

	/*查询记录数.*/
	public int doSearchCount(T vo);

	/*插入数据.*/
	public int doInsertByVO(T vo);

	/*批量插入.*/
	public int doBatchInsertByList(List<T> list);

	/*Map方式插入.*/
	public int doInsertByMap(Map<String, Object> param);

	/*通过ID删除.*/
	public int doDeleteById(String id);

	/*通过对象删除.*/
	public int doDeleteByVO(T vo);

	/*Map方式删除.*/
	public int doDeleteByMap(Map<String, Object> param);

	/*通过对象更新.*/
	public int doUpdateByVO(T vo);

	/*批量更新.*/
	public int doBatchUpdateByList(List<T> list);

	/*Map方式更新.*/
	public int doUpdateByMap(Map<String, Object> param);

	/*通过ID查询.*/
	public T doFindById(String id);

	/*通过对象查询，返回一个对象.*/
	public T doFindByVO(T vo);

	/*通过对象查询，返回列表.*/
	public List<T> doSearchListByVO(T vo);

	/*通过Map进行查询.*/
	public List<T> doSearchListByMap(Map<String, Object> param);

}
