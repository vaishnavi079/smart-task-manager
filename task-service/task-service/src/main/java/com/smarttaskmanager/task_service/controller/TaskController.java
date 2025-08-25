package com.smarttaskmanager.task_service.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.smarttaskmanager.task_service.bean.TaskDto;
import com.smarttaskmanager.task_service.service.TaskService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/task")
public class TaskController {
      
	@Autowired
	private TaskService taskServiceImpl; 
	
	@PostMapping("/createTask")
	public ResponseEntity<String> createTask(@RequestBody @Valid TaskDto taskDto, BindingResult result, HttpServletRequest request){
		if(result.hasErrors()) {
			String error = result.getFieldErrors().stream()
					       .map(err -> err.getField()+": "+err.getDefaultMessage())
					       .collect(Collectors.joining(", "));
			return ResponseEntity.badRequest().body(error);
		}
		int userId = (int) request.getAttribute("userId");
		String email = (String) request.getAttribute("email");
		return taskServiceImpl.createTask(userId, email, taskDto);
	}
	
	@GetMapping("/get")
	public ResponseEntity<List<TaskDto>> getAllTasks(){
		return taskServiceImpl.getAllTasks();
	}
	
	@GetMapping("/get/{taskId}")
	public ResponseEntity<TaskDto> getTask(@PathVariable int taskId){
		return taskServiceImpl.getTask(taskId);
	}
	
	@GetMapping("/getTasks")
	public List<TaskDto> getTasksByUserId(@RequestParam int userId){
		return taskServiceImpl.getTasksByUserId(userId);
	}
	
	@PutMapping("/update")
	public ResponseEntity<String> updateTask(@RequestBody @Valid TaskDto taskDto, BindingResult result){
		if(result.hasErrors()) {
			String error = result.getFieldErrors().stream()
					       .map(err -> err.getField()+": "+err.getDefaultMessage())
					       .collect(Collectors.joining(", "));
			return ResponseEntity.badRequest().body(error);
		}
		return taskServiceImpl.updateTask(taskDto);
	}
	
	@DeleteMapping("/delete/{taskId}")
	public ResponseEntity<String> deleteTask(@PathVariable int taskId){
		return taskServiceImpl.deleteTask(taskId);
	}
	
	@DeleteMapping("/deleteTasks")
	public String deleteTasksByUserId(@RequestParam int userId){
		return taskServiceImpl.deleteTasksByUserId(userId);
	}
	
}
