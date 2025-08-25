package com.smarttaskmanager.task_service.bean;


import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TaskDto {
	
    private int id;
    
    @NotBlank
	private String title;
	
	@NotBlank
	private String description;
	
	@NotNull
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate dueDate;
	
	private int userId;
}
