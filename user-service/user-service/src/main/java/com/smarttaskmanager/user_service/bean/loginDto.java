package com.smarttaskmanager.user_service.bean;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class loginDto {
      
	  @NotBlank(message = "Username is required")
	  private String username;
	  
	  @NotBlank(message = "Password is required")
	  @Size(min = 8, message = "Password must be atleast 8 characters")
	  private String password;
}
