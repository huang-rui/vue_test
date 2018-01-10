package com.syfri.userserver.handle;

import com.syfri.userserver.domain.Result;
import com.syfri.userserver.exception.UserException;
import com.syfri.userserver.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 捕获异常的句柄
 * //@ExceptionHandle统一处理某一类异常
 * //@ControllerAdvice异常集中处理，更好的使业务逻辑和异常处理相分离
 * //@ResponseStatus可以将某种异常映射为HTTP状态码
 * @author li.xue  2017/11/20
 */
@ControllerAdvice
public class ExceptionHandle {

	private static final Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

	@ExceptionHandler(value = UserException.class)
	@ResponseBody
	public Result handle(Exception e){
		if(e instanceof UserException){
			UserException userException = (UserException) e;
			logger.info("【自定义异常UserException】{}",userException.getMessage());
			return ResultUtil.error(userException.getCode(), userException.getMessage());
		}else {
			logger.info("【系统异常】{}",e.getMessage());
			return ResultUtil.error(-1, "未知错误！");
		}
	}
}
