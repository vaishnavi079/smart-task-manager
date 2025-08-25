package com.smarttaskmanager.user_service.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.smarttaskmanager.user_service.bean.TaskDto;
import com.smarttaskmanager.user_service.bean.UserDto;
import com.smarttaskmanager.user_service.bean.loginDto;
import com.smarttaskmanager.user_service.dao.UserDao;
import com.smarttaskmanager.user_service.feign.UserInterface;
import com.smarttaskmanager.user_service.model.Userr;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserService {
	
	@Autowired
	private UserInterface userInterface;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private PasswordEncoder passEncoder;
	
	@Autowired
	private JwtService jwtService;
	
	public ResponseEntity<String> register(UserDto userDto) {
		String encodedPassword = passEncoder.encode(userDto.getPassword());
		userDto.setPassword(encodedPassword);
		Userr user = new Userr();
		convertBeanToEntity(userDto, user);
		userDao.save(user);
		return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
	}

	public ResponseEntity<String> login(loginDto loginDto) {
		Userr user = userDao.findByUsername(loginDto.getUsername());
		if(user == null) {
           throw new UsernameNotFoundException("Invalid username");
		}

        if (!passEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        String token = jwtService.generateToken(user.getUserId(), user.getUsername(), user.getEmail());
        return new ResponseEntity<>(token, HttpStatus.OK);
	}
	
	private void convertBeanToEntity(UserDto uBean, Userr uEntity) {
			BeanUtils.copyProperties(uBean, uEntity);
	}

	public List<TaskDto> getTasks(HttpServletRequest request) {
		   int userId = (int) request.getAttribute("userId");
		   return userInterface.getTasksByUserId(userId);
	}

	public String deleteTasks(HttpServletRequest request) {
		int userId = (int) request.getAttribute("userId");
		return userInterface.deleteTasksByUserId(userId);
	}
	
	
}
