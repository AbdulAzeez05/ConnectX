package com.ConnectX.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ConnectX.Services.UserService;
import com.ConnectX.UserRepository.UserRepository;
import com.ConnectX.exceptions.UserException;
import com.ConnectX.models.User;



@RestController
public class UserController {
	
	@Autowired
	UserRepository userRepository;
	@Autowired
	UserService userService;
	


	@GetMapping("/api/users")
	public List<User> getUsers()
	{
		List<User> users=userRepository.findAll();
		return users;
	}
	@GetMapping("/api/users/{userId}")
	public User getUserById(@PathVariable("userId") Integer id) throws UserException
	{
		User user=userService.findUserById(id);
		return user;

	}
	
	

	
	@PutMapping("/api/users")
	public User updateUser(@RequestHeader("Authorization") String jwt,@RequestBody User user) throws UserException
	{
		User reqUser=userService.findUserProfileByJwt(jwt);
		
		User updatedUser=userService.updateUser(user, reqUser.getId());
		return updatedUser;

		
	}
	
	@DeleteMapping("/api/users/{userId}")
	public String deleteUser(@PathVariable Integer userId) throws UserException
	{
		Optional<User> user=userRepository.findById(userId);
		if(user.isEmpty())
		{
			throw new UserException( "User not exist with userid :"+ userId);
		}
		userRepository.delete(user.get());
		
		return "user deleted with id"+userId;
	}
	
	@PutMapping("/api/users/follow/{userId2}")
	public User followUserHandler(@RequestHeader("Authorization")String jwt ,@PathVariable Integer userId2) throws UserException
	{
		User reqUser=userService.findUserProfileByJwt(jwt);
		User user=userService.followUser(reqUser.getId(), userId2);
		return user;
	}
	
	
	@GetMapping("/api/users/search")
	public List<User> searchUser(@RequestParam("query") String query)
	
	{
		List<User> users=userService.searchUser(query);
		return users;
	}
	
	@GetMapping("/api/users/profile")
	public User getUserFromToken(@RequestHeader("Authorization")String jwt ) throws UserException
	{
		User user=userService.findUserProfileByJwt(jwt);
		user.setPassword(null);
		return user;
	}
	
	
	


}