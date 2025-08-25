package com.smarttaskmanager.task_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;


@OpenAPIDefinition(
	    info = @Info(title = "Smart Task Manager API", version = "1.0", description = "Handles all task-related operations"),
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
public class TaskServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskServiceApplication.class, args);
	}

}
