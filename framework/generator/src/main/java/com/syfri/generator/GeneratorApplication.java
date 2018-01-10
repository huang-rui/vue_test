package com.syfri.generator;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;


@SpringBootApplication
@ServletComponentScan
public class GeneratorApplication extends SpringBootServletInitializer implements CommandLineRunner{
	public static void main(String[] args) {
		SpringApplication.run(GeneratorApplication.class, args);
	}


	@Override
	public void run(String... strings) throws Exception {
		System.out.println("SpringBoot启动完成");
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
		return builder.sources(GeneratorApplication.class);
	}
}