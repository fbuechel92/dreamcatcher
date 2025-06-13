package com.dreamcatcher.mobile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
public class MobileApplication {
	public static void main(String[] args) {
		SpringApplication.run(MobileApplication.class, args);
	}
}
