package com.syfri.demo.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * dubbo消费者属性配置信息
 * @author  li.xue   2017/11/24
 */
@Component
@ConfigurationProperties(prefix = "dubbo.consumer")
public class DubboProperties {

	/*消费者名称.*/
	private String applicationName;

	/*服务类型.*/
	private String registryProtocol;

	/*服务地址.*/
	private String registryAddress;

	/*监控类型.*/
	private String monitorProtocol;

	/*失败重试次数.*/
	private Integer refrenceRetries;

	/*方法调用超时.*/
	private Integer refrenceTimeout;

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getRegistryProtocol() {
		return registryProtocol;
	}

	public void setRegistryProtocol(String registryProtocol) {
		this.registryProtocol = registryProtocol;
	}

	public String getRegistryAddress() {
		return registryAddress;
	}

	public void setRegistryAddress(String registryAddress) {
		this.registryAddress = registryAddress;
	}

	public String getMonitorProtocol() {
		return monitorProtocol;
	}

	public void setMonitorProtocol(String monitorProtocol) {
		this.monitorProtocol = monitorProtocol;
	}

	public Integer getRefrenceRetries() {
		return refrenceRetries;
	}

	public void setRefrenceRetries(Integer refrenceRetries) {
		this.refrenceRetries = refrenceRetries;
	}

	public Integer getRefrenceTimeout() {
		return refrenceTimeout;
	}

	public void setRefrenceTimeout(Integer refrenceTimeout) {
		this.refrenceTimeout = refrenceTimeout;
	}
}
