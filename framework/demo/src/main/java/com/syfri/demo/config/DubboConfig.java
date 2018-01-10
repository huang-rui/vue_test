package com.syfri.demo.config;

import com.alibaba.dubbo.config.*;
import com.alibaba.dubbo.config.spring.AnnotationBean;
import com.syfri.demo.properties.DubboProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 消费者的Dubbo配置
 * @author  li.xue  2017/11/24
 */
@Configuration
@EnableConfigurationProperties(com.syfri.demo.properties.DubboProperties.class)
@ConditionalOnClass({AnnotationBean.class,ApplicationConfig.class,ProtocolConfig.class,RegistryConfig.class})
public class DubboConfig {

	private static final Logger logger = LoggerFactory.getLogger(DubboConfig.class);

	@Autowired
	private DubboProperties dubboProperties;

	/**
	 * 由<dubbo:application>转换而来，注入dubbo上下文
	 * @author li.xue  2017/11/24
	 */
	@Bean
	@ConditionalOnMissingBean(ApplicationConfig.class)
	public ApplicationConfig applicationConfig(){
		ApplicationConfig applicationConfig = new ApplicationConfig();
		applicationConfig.setLogger("slf4j");
		applicationConfig.setName(dubboProperties.getApplicationName());
		logger.info("【DUBBO】dubbo.consumer.application.name = " + dubboProperties.getApplicationName());

		MonitorConfig monitorConfig = new MonitorConfig();
		monitorConfig.setProtocol(dubboProperties.getMonitorProtocol());
		applicationConfig.setMonitor(monitorConfig);
		logger.info("【DUBBO】dubbo.consumer.monitor.protocal = " + dubboProperties.getMonitorProtocol());

		return applicationConfig;
	}

	/**
	 * 由<dubbo:annotation>转换而来
	 * 扫描带有@com.alibaba.dubbo.init.annotation.Reference的注解
	 * @author  li.xue  2017/11/24
	 */
	@Bean
	public static AnnotationBean annotationBean(@Value("${dubbo.consumer.annotationPackage}") String packages){
		AnnotationBean annotationBean = new AnnotationBean();
		annotationBean.setPackage(packages);
		logger.info("【DUBBO】dubbo.consumer.annotation.package = " + packages);
		return annotationBean;

	}

	/**
	 * 由<dubbo:registry>转换而来，注入dubbo注册中心配置，基于zookeeper
	 * @author  li.xue  2017/11/24
	 */
	@Bean
	public RegistryConfig registryConfig() {
		RegistryConfig registryConfig = new RegistryConfig();

		registryConfig.setAddress(dubboProperties.getRegistryAddress());
		logger.info("【DUBBO】dubbo.consumer.registry.address = " + dubboProperties.getRegistryAddress());

		registryConfig.setProtocol(dubboProperties.getRegistryProtocol());
		logger.info("【DUBBO】dubbo.consumer.registry.protocol = " + dubboProperties.getRegistryProtocol());
		return registryConfig;
	}

	/**
	 * 由<dubbo:reference>转换而来
	 * @author  li.xue  2017/11/24
	 */
	@Bean
	public ReferenceConfig referenceConfig(){
		ReferenceConfig referenceConfig = new ReferenceConfig();

		referenceConfig.setRetries(dubboProperties.getRefrenceRetries());
		logger.info("【DUBBO】dubbo.consumer.reference.retries: " + dubboProperties.getRefrenceRetries());

		referenceConfig.setTimeout(dubboProperties.getRefrenceTimeout());
		logger.info("【DUBBO】dubbo.consumer.reference.timeout: " + dubboProperties.getRefrenceTimeout());

		return referenceConfig;
	}

}
