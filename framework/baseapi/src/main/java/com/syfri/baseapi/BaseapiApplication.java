package com.syfri.baseapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;


@SpringBootApplication
@ServletComponentScan
public class BaseapiApplication {
	public static void main(String[] args) {
		SpringApplication.run(BaseapiApplication.class, args);
	}
}