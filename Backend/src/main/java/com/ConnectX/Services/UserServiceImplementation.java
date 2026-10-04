package com.ConnectX.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ConnectX.UserRepository.UserRepository;
import com.ConnectX.config.JwtProvider;
import com.ConnectX.exceptions.UserException;
import com.ConnectX.models.User;

@Service
public class UserServiceImplementation implements UserService {
	@Autowired
	UserRepository userRepository;

	@Override
	public User registerUser(User user) {
		// TODO Auto-generated method stub
		User newUser = new User();
		newUser.setEmail(user.getEmail());
		newUser.setFirstName(user.getFirstName());
		newUser.setLastName(user.getLastName());
		newUser.setPassword(user.getPassword());
		newUser.setId(user.getId());
		User savedUser = userRepository.save(newUser);
		return savedUser;
	}

	@Override
	public User findUserById(Integer userId) throws UserException {
		
		Optional<User> user=userRepository.findById(userId);
		if(user.isPresent())
		{
			return user.get();
		}


		throw new UserException("User not found with userid"+userId);
	}

	@Override
	public User findUserByEmail(String email) {
		User user=userRepository.findByEmail(email);
		// TODO Auto-generated method stub
		return user;
	}

	@Override
	public User followUser(Integer reqUserId, Integer userId2) throws UserException {
		User reqUser=findUserById(reqUserId);
		
		User user2=findUserById(userId2);
		user2.getFollowers().add(reqUser.getId());
		reqUser.getFollowing()
.add(user2.getId());		


		userRepository.save(reqUser);
		userRepository.save(user2);
		return reqUser;
	}

	@Override
	public User updateUser(User user,Integer userId) throws UserException {
		Optional<User> user1= userRepository.findById(userId);
		if(user1.isEmpty())
		{
			throw new UserException("User doesnt exist with ID:"+userId);
		}
		User olduser=user1.get();
		


		if(user.getFirstName()!=null)
		{
			olduser.setFirstName(user.getFirstName());
		}
		if(user.getLastName()!=null)
		{
			olduser.setLastName(user.getLastName());
		} 
		if(user.getEmail()!=null)
		{
			olduser.setEmail(user.getEmail());
		}
		if(user.getGender()!=null)
		{
			olduser.setGender(user.getGender());
		}
		User updatedUser=userRepository.save(olduser);
		return updatedUser;
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<User> searchUser(String query) {
		return userRepository.searchUser(query);
		
	}

	// @Override
	// public User findUserProfileByJwt(String jwt) throws UserException {
	// 	String email=JwtProvider.getEmailFromJwtToken(jwt);
		
	// 	System.out.println("email"+email);
		
	// 	User user=userRepository.findByEmail(email);
		
	// 	if(user!=null) {
	// 		throw new UserException("user not exist with email "+email);
	// 	}
	// 	System.out.println("email user "+user.getEmail());
	// 	return user;
	// 	// TODO Auto-generated method stub
		
	// }
	@Override
public User findUserProfileByJwt(String jwt) throws UserException {
	String email = JwtProvider.getEmailFromJwtToken(jwt);

	User user = userRepository.findByEmail(email);

	if (user == null) {
		throw new UserException("user not exist with email " + email);
	}
	return user;
}
	

}