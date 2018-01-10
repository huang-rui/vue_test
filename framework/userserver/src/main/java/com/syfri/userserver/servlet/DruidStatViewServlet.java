package com.syfri.userserver.servlet;

import com.alibaba.druid.support.http.StatViewServlet;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
/**
 * 注册Druid监控用的servlet
 * @author li.xue 2017.11.15
 */

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/druid/*",
		initParams={
				@WebInitParam(name="allow",value="127.0.0.1"),// IP白名单 (没有配置或者为空，则允许所有访问)
				@WebInitParam(name="deny",value="192.168.16.111"),// IP黑名单 (存在共同时，deny优先于allow)
				@WebInitParam(name="loginUsername",value="admin"),// 用户名
				@WebInitParam(name="loginPassword",value="1"),// 密码
				@WebInitParam(name="resetEnable",value="false")// //是否能重置数据
		})
public class DruidStatViewServlet extends StatViewServlet {
}
