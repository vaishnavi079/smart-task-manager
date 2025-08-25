package com.smarttaskmanager.task_service.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.smarttaskmanager.common.bean.NotificationDto;
import com.smarttaskmanager.task_service.bean.TaskDto;
import com.smarttaskmanager.task_service.dao.TaskDaoWrapper;

@Service
public class TaskServiceImpl implements TaskService{

	@Autowired
	private TaskDaoWrapper daoWrapper;
	
	@Autowired
	private KafkaTemplate<String, NotificationDto> kafkaTemplate;
	
	public ResponseEntity<List<TaskDto>> getAllTasks() {
		List<TaskDto> lis = daoWrapper.getAllTasks();
		return new ResponseEntity<>(lis, HttpStatus.FOUND);
	}

	public ResponseEntity<String> createTask(int userId, String email, TaskDto taskDto) {
		daoWrapper.createTask(userId, taskDto);
		NotificationDto notifyProduce = new NotificationDto();
		notifyProduce.setEmail(email);
		notifyProduce.setMessage("Your task '"+taskDto.getTitle()+"' is successfully created");
		kafkaTemplate.send("Email-Notification", notifyProduce);
		return new ResponseEntity<>("Task is created successfully", HttpStatus.CREATED);
	}

	public ResponseEntity<String> updateTask(TaskDto taskDto) {
		daoWrapper.updateTask(taskDto);
		return new ResponseEntity<>("Update success", HttpStatus.OK);
	}

	public ResponseEntity<String> deleteTask(int taskId) {
		daoWrapper.deleteTask(taskId);
		return new ResponseEntity<>("Delete success", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<TaskDto> getTask(int taskId) {
		TaskDto taskDto = daoWrapper.getTask(taskId);
		return new ResponseEntity<>(taskDto, HttpStatus.FOUND);
	}

	@Override
	public List<TaskDto> getTasksByUserId(int userId) {
		List<TaskDto> tasks = daoWrapper.getTasksByUserId(userId);
	    return tasks;
	}

	@Override
	public String deleteTasksByUserId(int userId) {
		daoWrapper.deleteTasksByUserId(userId);
		return "Tasks are deleted successfully";
	}

}
