package com.angerasilas.health_system_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.angerasilas.health_system_backend")
public class HealthSystemBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(HealthSystemBackendApplication.class, args);
	}

}
