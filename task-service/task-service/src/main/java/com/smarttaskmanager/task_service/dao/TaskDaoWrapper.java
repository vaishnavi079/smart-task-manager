package com.smarttaskmanager.task_service.dao;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.smarttaskmanager.task_service.bean.TaskDto;
import com.smarttaskmanager.task_service.model.Task;


@Repository
public class TaskDaoWrapper {

	@Autowired
	private TaskDao taskDao;

	public List<TaskDto> getAllTasks() {
		List<Task> tasks = taskDao.findAll();
		List<TaskDto> tasksBean = new ArrayList<>();
		for(Task tEntity : tasks) {
			TaskDto tBean = new TaskDto();
			convertEntityToBean(tEntity, tBean);
			tasksBean.add(tBean);
		}
		return tasksBean;
		
	}

	private void convertEntityToBean(Task tEntity, TaskDto tBean) {
		BeanUtils.copyProperties(tEntity, tBean);
	}
	
	private void convertBeanToEntity(TaskDto tBean, Task tEntity) {
		BeanUtils.copyProperties(tBean, tEntity);
	}

	public void createTask(int userId, TaskDto taskDto) {
		taskDto.setUserId(userId);
		Task tEntity = new Task();
		convertBeanToEntity(taskDto, tEntity);
		taskDao.save(tEntity);
	}

	public void updateTask(TaskDto taskDto) {
		Task tEntity = taskDao.findById(taskDto.getId()).get();
		convertBeanToEntity(taskDto, tEntity);
		taskDao.save(tEntity);
	}

	public void deleteTask(int taskId) {
		taskDao.deleteById(taskId);
	}

	public TaskDto getTask(int taskId) {
		Task task = taskDao.findById(taskId).get();
		TaskDto taskDto = new TaskDto();
		convertEntityToBean(task, taskDto);
		return taskDto;
	}

	public List<TaskDto> getTasksByUserId(int userId) {
	    List<Task> tasks = taskDao.findByUserId(userId);
	    List<TaskDto> taskDtos = new ArrayList<>();
	    for(Task task : tasks) {
	    	TaskDto taskDto = new TaskDto();
	    	convertEntityToBean(task, taskDto);
	    	taskDtos.add(taskDto);
	    }
		return taskDtos;
	}

	public void deleteTasksByUserId(int userId) {
		taskDao.deleteByUserId(userId);
	}
	
	
	
}
