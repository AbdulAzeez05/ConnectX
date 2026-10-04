package com.ConnectX.UserRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ConnectX.models.User;

public interface UserRepository extends JpaRepository<User,Integer>{
	public User findByEmail(String email);
	
	@Query("select u from User u where u.firstName LIKE %:query% OR u.lastName Like %:query% Or u.email LIKE %:query%")
	public List<User> searchUser(@Param("query") String query);

}