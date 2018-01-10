package com.syfri.baseapi.model;

import com.alibaba.fastjson.JSONObject;
import com.syfri.baseapi.utils.EConstants;
import com.syfri.baseapi.utils.ErrorCodeUtil;

import java.io.Serializable;

/**
 * 封装的结果集
 * @author  li.xue  2017/12/5
 */
public class ResultVO implements Serializable{

	private static final long serialVersionUID = 1L;

	private String code = EConstants.CODE.SUCCESS;
	private String msg = "";
	private Object result = "";

	public static ResultVO build(){
		return new ResultVO();
	}

	public static ResultVO build(String code){
		return new ResultVO(code);
	}

	public static ResultVO build(String code, Object result){
		return new ResultVO(code, result);
	}

	private ResultVO(){
	}

	private ResultVO(String code){
		this.code = code;
	}

	private ResultVO(String code, Object result){
		this.code = code;
		this.result = result;
	}

	public String toJson(){
		return JSONObject.toJSONString(this);
	}

	public String getCode(){
		return code;
	}

	public void setCode(String code){
		this.code = code;
		this.msg = ErrorCodeUtil.getErrorDesc(code);
	}

	public String getMsg(){
		return msg;
	}

	public void setMsg(String msg){
		this.msg = msg;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}
}
