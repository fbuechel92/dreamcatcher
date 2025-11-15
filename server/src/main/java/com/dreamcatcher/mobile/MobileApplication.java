package com.dreamcatcher.mobile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class MobileApplication {
	public static void main(String[] args) {
		SpringApplication.run(MobileApplication.class, args);
	}
}
