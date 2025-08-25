package com.smarttaskmanager.user_service.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.smarttaskmanager.user_service.model.Userr;

@Repository
public interface UserDao extends JpaRepository<Userr, Integer>{

	public Userr findByUsername(String username);

}
