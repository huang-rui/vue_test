package com.syfri.userserver.filter;

import com.alibaba.druid.support.http.WebStatFilter;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

/**
 * 注册Druid监控的Filter
 * @author li.xue 2017.11.15
 */
@WebFilter(filterName = "druidWebStatFilter", urlPatterns = "/",
		initParams = {@WebInitParam(name="exclusions",value="*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*")})
public class DruidWebStatFilter extends WebStatFilter{}