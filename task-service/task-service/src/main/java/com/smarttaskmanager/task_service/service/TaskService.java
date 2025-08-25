package com.smarttaskmanager.task_service.service;


import java.util.List;
import org.springframework.http.ResponseEntity;
import com.smarttaskmanager.task_service.bean.TaskDto;

public interface TaskService {
	
	public ResponseEntity<List<TaskDto>> getAllTasks();
	public ResponseEntity<String> createTask(int userId, String email, TaskDto taskDto);
    public ResponseEntity<String> updateTask(TaskDto taskDto);
	public ResponseEntity<String> deleteTask(int taskId);
	public ResponseEntity<TaskDto> getTask(int taskId);
	public List<TaskDto> getTasksByUserId(int userId);
	public String deleteTasksByUserId(int userId);
}
