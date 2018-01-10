package com.syfri.baseapi.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 切面日志
 * @author li.xue  2017/11/20
 */

@Aspect
@Component
public class HttpAspect {

	private static final Logger logger = LoggerFactory.getLogger(HttpAspect.class);

	@Pointcut("execution(public * com.syfri.baseapi.controller.BaseController.*(..))")
	public void log(){}

	@Before("log()")
	public void doBefore(JoinPoint joinPoint){

		logger.info("方法开始前do something");

		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		//url
		logger.info("url={}",request.getRequestURL());
		//method
		logger.info("method={}",request.getMethod());
		//ip
		logger.info("ip={}",request.getRemoteAddr());
		//类方法
		logger.info("class_mothod={}",joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName());
		//参数
		logger.info("args={}",joinPoint.getArgs());
	}

	@After("log()")
	public void doAfter(){
		logger.info("方法结束后do something");
	}

	@AfterReturning(returning = "object", pointcut = "log()")
	public void doAfterReturning(Object object){
		logger.info("response={}", object.toString());
	}
}
