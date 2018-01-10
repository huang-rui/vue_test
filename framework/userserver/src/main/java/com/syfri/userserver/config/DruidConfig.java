package com.syfri.userserver.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.syfri.userserver.properties.DruidProperties;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 1.采用Druid连接池的dataSource的配置
 * 2.配置Druid监控
 * @author li.xue  2017/11/15
 */

@Configuration
@EnableConfigurationProperties(DruidProperties.class)
@MapperScan(basePackages = "com.syfri.userservice.mapper")
public class DruidConfig {

	private static final Logger logger = LoggerFactory.getLogger(DruidConfig.class);

	@Autowired
	private DruidProperties druidProperties;

	@Autowired
	private Environment environment;

	/**
	 * 配置DataSource
	 *
	 * @author li.xue  2017/11/15
	 */
	@Bean(name = "baseDataSource")
	@Primary //这里定义的DataSource将覆盖其他来源的DataSource
	public DruidDataSource dataSource() {
		DruidDataSource dataSource = new DruidDataSource();

		dataSource.setUrl(druidProperties.getUrl());
		dataSource.setUsername(druidProperties.getUsername());
		dataSource.setPassword(druidProperties.getPassword());
		dataSource.setDriverClassName(druidProperties.getDriverClassName());

		dataSource.setInitialSize(druidProperties.getInitialSize());
		dataSource.setMinIdle(druidProperties.getMinIdle());
		dataSource.setMaxActive(druidProperties.getMaxActive());
		dataSource.setMaxWait(druidProperties.getMaxWait());
		dataSource.setTimeBetweenEvictionRunsMillis(druidProperties.getTimeBetweenEvictionRunsMillis());
		dataSource.setMinEvictableIdleTimeMillis(druidProperties.getMinEvictableIdleTimeMillis());
		dataSource.setValidationQuery(druidProperties.getValidationQuery());
		dataSource.setTestWhileIdle(druidProperties.isTestWhileIdle());
		dataSource.setTestOnBorrow(druidProperties.isTestOnBorrow());
		dataSource.setTestOnReturn(druidProperties.isTestOnReturn());
		dataSource.setPoolPreparedStatements(druidProperties.isPoolPreparedStatements());
		dataSource.setMaxPoolPreparedStatementPerConnectionSize(druidProperties.getMaxPoolPreparedStatementPerConnectionSize());

		try {
			//sql监控
			dataSource.setFilters(druidProperties.getFilters());
		} catch (SQLException e) {
			logger.error("druid configuration initialization filter", e);
		}

		String connectionPropertiesStr = druidProperties.getConnectionProperties();
		if (connectionPropertiesStr != null && !"".equals(connectionPropertiesStr)) {
			Properties connectionProperties = new Properties();
			String[] propertiesList = connectionPropertiesStr.split(";");
			for (String temp : propertiesList) {
				String[] obj = temp.split("=");
				connectionProperties.put(obj[0], obj[1]);
			}
			dataSource.setConnectProperties(connectionProperties);
		}
		return dataSource;
	}

	/**
	 * 根据数据源创建SqlSessionFactory
	 * 官方提供的可配置参数有：
	 * config-location，mapper-locations，type-aliases-package，type-handlers-package，
	 * executor-type(SIMPLE, REUSE, BATCH [optional]),configuration
	 *
	 * @author li.xue  2017/11/17
	 */
	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource ds) throws Exception {
		SqlSessionFactoryBean ssf = new SqlSessionFactoryBean();
		ssf.setDataSource(ds);
		if (environment.containsProperty("mybatis.config-location")) {
			ssf.setConfigLocation(new PathMatchingResourcePatternResolver().getResource(environment.getProperty("mybatis.config-location")));
		}
		if (environment.containsProperty("mybatis.mapper-locations")) {
			ssf.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(environment.getProperty("mybatis.mapper-locations")));
		}
		if (environment.containsProperty("mybatis.type-aliases-package")) {
			ssf.setTypeAliasesPackage(environment.getProperty("mybatis.type-aliases-package"));
		}
		if (environment.containsProperty("mybatis.type-handlers-package")) {
			ssf.setTypeHandlersPackage(environment.getProperty("mybatis.type-handlers-package"));
		}

		//Configuration的设置，目前只配置了cache-enabled
		org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
		//cache-enabled
		if (environment.containsProperty("mybatis.configuration.cache-enabled")) {
			String temp = environment.getProperty("mybatis.configuration.cache-enabled");
			if ("true".equals(temp)) {
				configuration.setCacheEnabled(true);
			} else {
				configuration.setCacheEnabled(false);
			}
		}
		//lazy-loading-enabled
		if (environment.containsProperty("mybatis.configuration.lazy-loading-enabled")) {
			String temp = environment.getProperty("mybatis.configuration.lazy-loading-enabled");
			if ("true".equals(temp)) {
				configuration.setLazyLoadingEnabled(true);
			} else {
				configuration.setLazyLoadingEnabled(false);
			}
		}
		ssf.setConfiguration(configuration);
		return ssf.getObject();
	}
}