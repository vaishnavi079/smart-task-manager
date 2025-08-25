package com.smarttaskmanager.task_service.model;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

@Data
@Entity
public class Task {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String title;
	
	private String description;
	
	private LocalDate dueDate;
	
	private LocalDate createdAt;
	
	private LocalDate updatedAt;
	
	private int userId;
	
	@PrePersist
	public void create() {
		createdAt = updatedAt = LocalDate.now();
	}
	
	@PreUpdate
	public void update() {
		updatedAt = LocalDate.now();
	}

	

}
