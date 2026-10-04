package com.ConnectX.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.ConnectX.Services.CommentService;
import com.ConnectX.Services.UserService;
import com.ConnectX.models.Comment;
import com.ConnectX.models.User;

@RestController
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private UserService userService;
	
	
	@PostMapping("/api/comments/post/{postId}")
	public Comment createComment(@RequestBody Comment comment,
			@RequestHeader("Authorization") String jwt,@PathVariable Integer postId) throws Exception
	{
		User user=userService.findUserProfileByJwt(jwt);
		Comment createdComment=commentService.createComment(comment, postId, user.getId());
		return createdComment;
	}
	@PutMapping("/api/comments/post/{commentId}")
	public Comment likeComment(
			@RequestHeader("Authorization") String jwt,
			@PathVariable Integer commentId) throws Exception
	{
		User user=userService.findUserProfileByJwt(jwt);
		Comment likedComment=commentService.likeComment(commentId,user.getId());
		return likedComment;
	}
	
	
	
	
	
	

}
