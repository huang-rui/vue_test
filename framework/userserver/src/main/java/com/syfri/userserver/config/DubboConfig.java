package com.syfri.userserver.config;

import com.alibaba.dubbo.config.*;
import com.alibaba.dubbo.config.spring.AnnotationBean;
import com.syfri.userserver.properties.DubboProperties;
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
 * Dubbo配置文件
 * @author  li.xue  2017/11/23
 */

@Configuration
@EnableConfigurationProperties(DubboProperties.class)
@ConditionalOnClass({AnnotationBean.class,ApplicationConfig.class,ProtocolConfig.class,RegistryConfig.class})
public class DubboConfig {

	private static final Logger logger = LoggerFactory.getLogger(DubboConfig.class);

	@Autowired
	private DubboProperties dubboProperties;

	/**
	 * 由<dubbo:application>转换而来，注入dubbo上下文
	 * @author li.xue  2017/11/23
	 */
	@Bean
	@ConditionalOnMissingBean(ApplicationConfig.class)
	public ApplicationConfig applicationConfig(){
		ApplicationConfig applicationConfig = new ApplicationConfig();
		applicationConfig.setLogger("slf4j");
		applicationConfig.setName(dubboProperties.getApplicationName());
		logger.info("【DUBBO】dubbo.provider.application.name: " + dubboProperties.getApplicationName());

		//监控
		if(!"-1".equals(dubboProperties.getMonitorProtocol())){
			MonitorConfig monitorConfig = new MonitorConfig();
			monitorConfig.setProtocol(dubboProperties.getMonitorProtocol());
			applicationConfig.setMonitor(monitorConfig);
			logger.info("【DUBBO】dubbo.provider.monitor.protocol: " + dubboProperties.getMonitorProtocol());
		}

		return applicationConfig;
	}

	/**
	 * 由<dubbo:annotation>转换而来
	 * 扫描带有@com.alibaba.dubbo.init.annotation.Reference的注解
	 * @author  li.xue  2017/11/23
	 */
	@Bean
	@ConditionalOnMissingBean(AnnotationBean.class)
	public static AnnotationBean annotationBean(@Value("${dubbo.provider.annotationPackage}") String packages){
		AnnotationBean annotationBean = new AnnotationBean();
		annotationBean.setPackage(packages);
		logger.info("【DUBBO】dubbo.provider.annotation.package: " + packages);
		return annotationBean;
	}

	/**
	 * 由<dubbo:registry>转换而来，注入dubbo注册中心配置，基于zookeeper
	 * @author  li.xue  2017/11/23
	 */
	@Bean
	@ConditionalOnMissingBean(RegistryConfig.class)
	public RegistryConfig registryConfig(){
		RegistryConfig registryConfig = new RegistryConfig();

		registryConfig.setProtocol(dubboProperties.getRegistryProtocol());
		logger.info("【DUBBO】dubbo.provider.registry.protocol: " + dubboProperties.getRegistryProtocol());

		registryConfig.setAddress(dubboProperties.getRegistryAddress());
		logger.info("【DUBBO】dubbo.provider.registry.address: " + dubboProperties.getRegistryAddress());

		return registryConfig;
	}

	/**
	 * 由<dubbo:protocal>转换而来，基于dubbo协议提供服务
	 * @author  li.xue  2017/11/23
	 */
	@Bean
	@ConditionalOnMissingBean(ProtocolConfig.class)
	public ProtocolConfig protocolConfig(){
		ProtocolConfig protocolConfig = new ProtocolConfig();

		protocolConfig.setName(dubboProperties.getprotocolName());
		logger.info("【DUBBO】dubbo.provider.protocol.name: " + dubboProperties.getprotocolName());

		protocolConfig.setPort(dubboProperties.getprotocolPort());
		logger.info("【DUBBO】dubbo.provider.protocol.port: " + dubboProperties.getprotocolPort());

		return protocolConfig;
	}

	/**
	 * 由<dubbo:service>转换而来
	 * @author  li.xue  2017/11/23
	 */
	@Bean
	@ConditionalOnMissingBean(ServiceConfig.class)
	public ServiceConfig serviceConfig(){
		ServiceConfig serviceConfig = new ServiceConfig();

		Integer serviceTimeout = dubboProperties.getServiceTimeout();
		if(serviceTimeout>0){
			serviceConfig.setTimeout(serviceTimeout);
			logger.info("【DUBBO】dubbo.provider.service.timeout: " + serviceTimeout);
		}

		Integer serviceRetries = dubboProperties.getServiceRetries();
		if(serviceTimeout>0){
			serviceConfig.setRetries(serviceRetries);
			logger.info("【DUBBO】dubbo.provider.service.retries: " + serviceRetries);
		}

//		serviceConfig.setGroup("aaa");
		return serviceConfig;
	}

	@Bean(name = "userProvider")
	public ProviderConfig providerConfig(ApplicationConfig applicationConfig, RegistryConfig registryConfig, ProtocolConfig protocolConfig){
		ProviderConfig providerConfig = new ProviderConfig();
		providerConfig.setTimeout(6000);
		providerConfig.setRetries(0);
		providerConfig.setDelay(-1);
		providerConfig.setApplication(applicationConfig);
		providerConfig.setRegistry(registryConfig);
		providerConfig.setProtocol(protocolConfig);
		return providerConfig;
	}
}
