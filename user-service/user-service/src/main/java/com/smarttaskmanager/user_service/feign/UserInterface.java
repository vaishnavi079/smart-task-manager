package com.smarttaskmanager.user_service.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smarttaskmanager.user_service.bean.TaskDto;

@FeignClient("TASK-SERVICE")
public interface UserInterface {
	
	
	@GetMapping("/task/getTasks")
	public List<TaskDto> getTasksByUserId(@RequestParam int userId);
	
	
	@DeleteMapping("/task/deleteTasks")
	public String deleteTasksByUserId(@RequestParam int userId);
	
}
