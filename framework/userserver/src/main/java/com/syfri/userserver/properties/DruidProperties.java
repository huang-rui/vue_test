package com.syfri.userserver.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Durid配置信息
 * 由于SpringBoot中默认支持的连接池有dbcp，dbcp2，tomcat，hikari这三种连接池,
 * 但是不支持Druid，故需要进行配置信息的定制
 * @author li.xue  2017.11.15
 */
@Component
@ConfigurationProperties(prefix = "spring.druid.datasource")
public class DruidProperties {

	private String url;

	private String username;

	private String password;

	private String driverClassName;

	/*初始化建立物理连接的个数.*/
	private int initialSize;

	/*连接池最小数量.*/
	private int minIdle;

	/*连接池最大数量.*/
	private int maxActive;

	/*获取连接等待超时的时间.*/
	private int maxWait;

	/*配置间隔多久进行一次检测，检测需要关闭空闲的连接，单位为毫秒.*/
	private int timeBetweenEvictionRunsMillis;

	/*配置连接池中最小的生存时间，单位是毫秒.*/
	private int minEvictableIdleTimeMillis;

	/*用来检测连接是否有效的sql，如果validationQuery为null，testOnBorrow、testOnReturn、testWhileIdle都不会其作用.*/
	private String validationQuery;

	/*申请连接时执行validationQuery检测连接是否有效，但是会降低性能.*/
	private boolean testWhileIdle;

	/*归还连接时执行validationQuery检测连接是否有效，但是会降低性能.*/
	private boolean testOnBorrow;

	private boolean testOnReturn;

	/*配置是否打开PSCache.*/
	private boolean poolPreparedStatements;

	/*配置每个连接上PSCache的大小.*/
	private int maxPoolPreparedStatementPerConnectionSize;

	/*配置监控统计拦截的filters，去掉后监控界面sql无法统计，'wall'用于防火墙.*/
	private String filters;

	/*通过connectProperties属性来打开mergeSql功能；慢SQL记录.*/
	private String connectionProperties;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	public int getInitialSize() {
		return initialSize;
	}

	public void setInitialSize(int initialSize) {
		this.initialSize = initialSize;
	}

	public int getMinIdle() {
		return minIdle;
	}

	public void setMinIdle(int minIdle) {
		this.minIdle = minIdle;
	}

	public int getMaxActive() {
		return maxActive;
	}

	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	}

	public int getMaxWait() {
		return maxWait;
	}

	public void setMaxWait(int maxWait) {
		this.maxWait = maxWait;
	}

	public int getTimeBetweenEvictionRunsMillis() {
		return timeBetweenEvictionRunsMillis;
	}

	public void setTimeBetweenEvictionRunsMillis(int timeBetweenEvictionRunsMillis) {
		this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
	}

	public int getMinEvictableIdleTimeMillis() {
		return minEvictableIdleTimeMillis;
	}

	public void setMinEvictableIdleTimeMillis(int minEvictableIdleTimeMillis) {
		this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
	}

	public String getValidationQuery() {
		return validationQuery;
	}

	public void setValidationQuery(String validationQuery) {
		this.validationQuery = validationQuery;
	}

	public boolean isTestWhileIdle() {
		return testWhileIdle;
	}

	public void setTestWhileIdle(boolean testWhileIdle) {
		this.testWhileIdle = testWhileIdle;
	}

	public boolean isTestOnBorrow() {
		return testOnBorrow;
	}

	public void setTestOnBorrow(boolean testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
	}

	public boolean isTestOnReturn() {
		return testOnReturn;
	}

	public void setTestOnReturn(boolean testOnReturn) {
		this.testOnReturn = testOnReturn;
	}

	public boolean isPoolPreparedStatements() {
		return poolPreparedStatements;
	}

	public void setPoolPreparedStatements(boolean poolPreparedStatements) {
		this.poolPreparedStatements = poolPreparedStatements;
	}

	public int getMaxPoolPreparedStatementPerConnectionSize() {
		return maxPoolPreparedStatementPerConnectionSize;
	}

	public void setMaxPoolPreparedStatementPerConnectionSize(int maxPoolPreparedStatementPerConnectionSize) {
		this.maxPoolPreparedStatementPerConnectionSize = maxPoolPreparedStatementPerConnectionSize;
	}

	public String getFilters() {
		return filters;
	}

	public void setFilters(String filters) {
		this.filters = filters;
	}

	public String getConnectionProperties() {
		return connectionProperties;
	}

	public void setConnectionProperties(String connectionProperties) {
		this.connectionProperties = connectionProperties;
	}
}
