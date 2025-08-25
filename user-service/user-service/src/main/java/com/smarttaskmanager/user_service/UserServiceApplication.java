package com.smarttaskmanager.user_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(
	    info = @Info(title = "Smart User API", version = "1.0", description = "Handles all user-related operations"),
	    security = {
	            @SecurityRequirement(name = "bearerAuth")
	        }
)
@SecurityScheme(
	        name = "bearerAuth",
	        type = SecuritySchemeType.HTTP,
	        scheme = "bearer",
	        bearerFormat = "JWT"
)

@SpringBootApplication
@EnableFeignClients(basePackages = {
	    "com.smarttaskmanager.user_service.feign",
	    "com.smarttaskmanager.user_service.config"
	})
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

}
