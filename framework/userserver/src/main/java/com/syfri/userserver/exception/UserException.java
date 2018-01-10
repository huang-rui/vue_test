package com.syfri.userserver.exception;

/**
 * 自定义一个异常的类，并为我们定义的异常类进行编码
 * @author  li.xue  2017/11/20
 */
public class UserException extends RuntimeException {
	/*错误代码.*/
	private Integer code;

	public UserException(Integer code, String message) {
		super(message);
		this.code = code;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}
}
