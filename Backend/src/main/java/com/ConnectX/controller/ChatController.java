package com.ConnectX.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.ConnectX.Services.ChatService;
import com.ConnectX.Services.UserService;
import com.ConnectX.models.Chat;
import com.ConnectX.models.User;
import com.ConnectX.request.ChatRequest;

@RestController
public class ChatController {
	
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ChatService chatService;
	
	
	@PostMapping("/api/chats")
	public Chat createChat(@RequestHeader("Authorization") String jwt,
			@RequestBody ChatRequest req) throws Exception
	{
		User reqUser=userService.findUserProfileByJwt(jwt);
		User user2=userService.findUserById(req.getUserId());
		
		Chat chat=chatService.createChat(reqUser, user2);
		return chat;
	}
	
	
	
	@GetMapping("/api/chats")
	public List<Chat> findUsersChat(@RequestHeader("Authorization") String jwt) throws Exception
	{
		User user=userService.findUserProfileByJwt(jwt);
		
		List<Chat> chats=chatService.findUsersChat(user.getId());
		return chats;
		
	}

}
