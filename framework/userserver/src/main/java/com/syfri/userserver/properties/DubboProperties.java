package com.syfri.userserver.properties;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Dubbo提供者（userservice）配置属性
 * @author li.xue  2017/11/23
 */

@Component
@ConfigurationProperties(prefix = "dubbo.provider")
public class DubboProperties {

	/*提供方应用信息.*/
	private String applicationName;

	/*注册中心暴露的服务类型.*/
	private String registryProtocol;

	/*注册中心暴露的服务地址.*/
	private String registryAddress;

	/*协议名称.*/
	private String protocolName;

	/*协议端口.*/
	private Integer protocolPort;

	/*监控.*/
	private String monitorProtocol;

	/*.*/
	private Integer serviceTimeout;

	/*.*/
	private Integer serviceRetries;

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

	public String getprotocolName() {
		return protocolName;
	}

	public void setprotocolName(String protocolName) {
		this.protocolName = protocolName;
	}

	public Integer getprotocolPort() {
		return protocolPort;
	}

	public void setprotocolPort(Integer protocolPort) {
		this.protocolPort = protocolPort;
	}

	public String getMonitorProtocol() {
		return monitorProtocol;
	}

	public void setMonitorProtocol(String monitorProtocol) {
		this.monitorProtocol = monitorProtocol;
	}

	public Integer getServiceTimeout() {
		return serviceTimeout;
	}

	public void setServiceTimeout(Integer serviceTimeout) {
		this.serviceTimeout = serviceTimeout;
	}

	public Integer getServiceRetries() {
		return serviceRetries;
	}

	public void setServiceRetries(Integer serviceRetries) {
		this.serviceRetries = serviceRetries;
	}

	public String getProtocolName() {
		return protocolName;
	}

	public void setProtocolName(String protocolName) {
		this.protocolName = protocolName;
	}

	public Integer getProtocolPort() {
		return protocolPort;
	}

	public void setProtocolPort(Integer protocolPort) {
		this.protocolPort = protocolPort;
	}
}
