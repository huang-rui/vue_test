package com.syfri.userserver.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.entity.GlobalConfiguration;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import java.io.IOException;

/**
 * Mybatis-plus配置文件
 * @author li.xue  2017.11.17
 */

//@Configuration
//@MapperScan(basePackages = "com.syfri.userservice.mapper")
public class MybatisPlusConfig {
	private static final Logger logger = LoggerFactory.getLogger(MybatisPlusConfig.class);

	@Autowired
	private Environment environment;

	/**
	 * 初始化Global-Configuration
	 * @author li.xue  2017.11.17
	 */
	@Bean(name = "globalConfig")
	public GlobalConfiguration globalConfig(){

		logger.info("初始化GlobalConfiguration");

		GlobalConfiguration globalConfiguration = new GlobalConfiguration();

		/*主键类型： 【0】自增，【1】用户输入ID，【2】数字类型唯一ID，【4】UUID*/
		if(environment.containsProperty("mybatis-plus.global-config.id-type")){
			globalConfiguration.setIdType(Integer.parseInt(environment.getProperty("mybatis-plus.global-config.id-type")));
		}

		/*字段策略：【0】忽略判断，【1】非NULL判断，【2】非空判断*/
		if(environment.containsProperty("mybatis-plus.global-config.field-strategy")){
			globalConfiguration.setFieldStrategy(Integer.parseInt(environment.getProperty("mybatis-plus.global-config.field-strategy")));
		}

		/*驼峰下还先转换*/
		if(environment.containsProperty("mybatis-plus.global-config.db-column-underline")){
			String temp = environment.getProperty("mybatis-plus.global-config.db-column-underline");
			if("true".equals(temp)){
				globalConfiguration.setDbColumnUnderline(true);
			}else{
				globalConfiguration.setDbColumnUnderline(false);
			}

		}

		if(environment.containsProperty("mybatis-plus.global-config.refresh-mapper")){
			String temp = environment.getProperty("mybatis-plus.global-config.refresh-mapper");
			if("true".equals(temp)){
				globalConfiguration.setRefresh(true);
			}else{
				globalConfiguration.setRefresh(false);
			}
		}

		/*数据库大写下划线转换*/
		if(environment.containsProperty("mybatis-plus.global-config.capital-mode")){
			String temp = environment.getProperty("mybatis-plus.global-config.capital-mode");
			if("true".equals(temp)){
				globalConfiguration.setCapitalMode(true);
			}else{
				globalConfiguration.setCapitalMode(false);
			}
		}

		/*逻辑删除配置*/
		if(environment.containsProperty("mybatis-plus.global-config.logic-delete-value")){
			globalConfiguration.setLogicDeleteValue(environment.getProperty("mybatis-plus.global-config.logic-delete-value"));
		}
		if(environment.containsProperty("global-config.logic-not-delete-value")) {
			globalConfiguration.setLogicNotDeleteValue(environment.getProperty("global-config.logic-not-delete-value"));
		}
		return globalConfiguration;
	}

	/**
	 * 配置SqlSessionFactory
	 * @author  li.xue  2017.11.17
	 */
	@Bean(name = "sqlSessionFactory")
	public SqlSessionFactory sqlSessionFactory(@Qualifier(value = "globalConfig") GlobalConfiguration globalConfig,
											   @Qualifier(value = "baseDataSource") DruidDataSource dataSource/*,
											   @Value("${mybatis-plus.mapper-locations}") String mapperLocations,
											   @Value("${mybatis-plus.config-location}") String configLocation,
											   @Value("${mybatis-plus.type-aliases-package}") String typeAliasesPackage*/)
								throws Exception{
		logger.info("初始化SqlSessionFactory开始");
		MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
		sqlSessionFactory.setDataSource(dataSource);
		sqlSessionFactory.setGlobalConfig(globalConfig);
		Interceptor[] interceptor = {new PaginationInterceptor()};
		sqlSessionFactory.setPlugins(interceptor);
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

		try{
			if(environment.containsProperty("mybatis-plus.mapper-locations")){
				String mapperLocations = environment.getProperty("mybatis-plus.mapper-locations");
				sqlSessionFactory.setMapperLocations(resolver.getResources(mapperLocations));
			}


			if(environment.containsProperty("mybatis-plus.config-location")){
				String configLocation = environment.getProperty("mybatis-plus.config-location");
				sqlSessionFactory.setConfigLocation(resolver.getResource(configLocation));
			}

			/*这个是实体类的位置，如果有多个package，用逗号或者分号隔开*/
			if(environment.containsProperty("mybatis-plus.type-aliases-package")){
				String typeAliasesPackage = environment.getProperty("mybatis-plus.type-aliases-package");
				sqlSessionFactory.setTypeAliasesPackage(typeAliasesPackage);
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
		logger.info("初始化sqlSessionFactory结束");
		return sqlSessionFactory.getObject();
	}

	/**
	 * 配置事务管理
	 * @author  li.xue  2017.11.17
	 */
	public DataSourceTransactionManager transactionManager(@Qualifier(value = "baseDataSource") DruidDataSource dataSource){
		logger.info("初始化DataSourceTransactionManager");
		return new DataSourceTransactionManager(dataSource);
	}

	/**
	 * Mybaits-plus分页插件
	 *
	 * @author li.xue  2017/11/16
	 */
	public PaginationInterceptor paginationInterceptor() {
		PaginationInterceptor page = new PaginationInterceptor();
		page.setDialectType("oracle");
		return page;
	}
}