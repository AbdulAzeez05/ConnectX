package com.ConnectX.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.ConnectX.Services.ReelsService;
import com.ConnectX.Services.UserService;
import com.ConnectX.models.Reels;
import com.ConnectX.models.User;

@RestController
public class ReelsController {
	
	@Autowired
	private ReelsService reelsService;
	
	@Autowired
	private UserService userService;
	
	
	@PostMapping("/api/reels")
	public Reels createReels(@RequestBody Reels reel,
			@RequestHeader("Authorization") String token) throws Exception
	{
		User reqUser=userService.findUserProfileByJwt(token);
		Reels createdReels=reelsService.createReel(reel, reqUser);
		
		return createdReels;
	}
	
	@GetMapping("/api/reels")
	public List<Reels> findAllReels() 
	{
		
		List<Reels> reels= reelsService.findAllReels();
		
		
		return reels;
	}
	
	
	@GetMapping("/api/reels/user/{userId}")
	public List<Reels> findUserReels(@PathVariable Integer userId) throws Exception 
	{
		
		List<Reels> reels= reelsService.findUsersReel(userId);
		return reels;
		
		
		
	}

	

}
