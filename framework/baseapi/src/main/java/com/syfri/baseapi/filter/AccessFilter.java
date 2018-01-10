package com.syfri.baseapi.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(filterName = "accessFilter", urlPatterns = "/*")
public class AccessFilter implements Filter{

	private static final Logger logger = LoggerFactory.getLogger(AccessFilter.class);

	protected Environment environment;

	public AccessFilter(Environment environment){
		this.environment = environment;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("--- AccessFilter Init ---");
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		filterChain.doFilter(servletRequest, servletResponse);
	}

	@Override
	public void destroy() {
		logger.info("--- AccessFilter Destroy ---");
	}
}
