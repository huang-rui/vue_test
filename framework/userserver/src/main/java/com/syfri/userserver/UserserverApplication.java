package com.syfri.userserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;


@SpringBootApplication
@ServletComponentScan
public class UserserverApplication {
	public static void main(String[] args) {
		SpringApplication.run(UserserverApplication.class, args);
	}
}