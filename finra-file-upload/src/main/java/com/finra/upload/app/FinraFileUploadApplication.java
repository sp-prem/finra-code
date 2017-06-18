package com.finra.upload.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaRepositories(basePackages ={"com.finra.upload.dao"})
@ComponentScan(basePackages = {"com.finra.upload.controller","com.finra.upload.service"})
@EntityScan(basePackages = "com.finra.upload.domain")
@EnableScheduling
public class FinraFileUploadApplication 
{
	public static void main(String[] args) 
	{
		SpringApplication.run(FinraFileUploadApplication.class, args);
	}
}