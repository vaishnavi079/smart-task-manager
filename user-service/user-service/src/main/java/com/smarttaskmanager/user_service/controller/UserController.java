package com.smarttaskmanager.user_service.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smarttaskmanager.user_service.bean.TaskDto;
import com.smarttaskmanager.user_service.bean.UserDto;
import com.smarttaskmanager.user_service.bean.loginDto;
import com.smarttaskmanager.user_service.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody @Valid UserDto userDto, BindingResult result){
		if(result.hasErrors()) {
			String error = result.getFieldErrors().stream()
					       .map(err -> err.getField()+": "+err.getDefaultMessage())
					       .collect(Collectors.joining(", "));
			return ResponseEntity.badRequest().body(error);
		}
		return userService.register(userDto);
		
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody @Valid loginDto loginDto, BindingResult result){
		if(result.hasErrors()) {
			String error = result.getFieldErrors().stream()
					       .map(err -> err.getField()+": "+err.getDefaultMessage())
					       .collect(Collectors.joining(", "));
			return ResponseEntity.badRequest().body(error);
		}
		return userService.login(loginDto);
	}
	
	@GetMapping("/getTasks")
	public List<TaskDto> getTasks(HttpServletRequest request){
		return userService.getTasks(request);
	}
	
	@DeleteMapping("/deleteTasks")
	public String deleteTasks(HttpServletRequest request){
		return userService.deleteTasks(request);
	}
}
