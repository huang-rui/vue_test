package com.syfri.userserver.util;

import com.syfri.userserver.domain.Result;

/**
 * 对返回结果的处理类
 * 确保在前台返回的数据都是同一种类型，包括code,msg,data
 * @author li.xue  2017/11/20
 */
public class ResultUtil {
	/**
	 * 成功方法的处理类
	 * @author  li.xue  2017/11/20
	 */
	public static Result success(Object object){
		Result result = new Result();
		result.setCode(0);
		result.setMsg("成功");
		result.setData(object);
		return result;
	}
	public static Result success(){
		return success(null);
	}

	/**
	 * 失败方法的处理类
	 * @author  li.xue  2017/11/20
	 */
	public static Result error(Integer code, String msg){
		Result result = new Result();
		result.setCode(code);
		result.setMsg(msg);
		result.setData(null);
		return result;
	}
}
