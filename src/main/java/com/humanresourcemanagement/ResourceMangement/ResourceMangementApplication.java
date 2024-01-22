package com.humanresourcemanagement.ResourceMangement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.humanresourcemanagement.ResourceMangement")
public class ResourceMangementApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResourceMangementApplication.class, args);
	}

}
