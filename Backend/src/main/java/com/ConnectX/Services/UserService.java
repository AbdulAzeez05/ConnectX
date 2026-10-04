package com.ConnectX.Services;

import java.util.List;

import com.ConnectX.exceptions.UserException;
import com.ConnectX.models.User;

public interface UserService {
	public User registerUser(User user);

	public User findUserById(Integer userId) throws UserException;

	public User findUserByEmail(String email);

	public User followUser(Integer userId1, Integer userId2) throws UserException;

	public User updateUser(User user,Integer Id) throws UserException;

	public List<User> searchUser(String query);
	
	public User findUserProfileByJwt(String jwt) throws UserException;

}