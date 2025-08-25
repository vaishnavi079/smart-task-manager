package com.smarttaskmanager.task_service.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.smarttaskmanager.task_service.model.Task;

@Repository
public interface TaskDao extends JpaRepository<Task, Integer>{
	
	List<Task> findByUserId(int userId);
	
	@Transactional
    @Modifying
	void deleteByUserId(int userId);
}
