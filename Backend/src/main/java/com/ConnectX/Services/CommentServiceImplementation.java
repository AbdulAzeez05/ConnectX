package com.ConnectX.Services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ConnectX.UserRepository.CommentRepository;
import com.ConnectX.UserRepository.PostRepository;
import com.ConnectX.models.Comment;
import com.ConnectX.models.Post;
import com.ConnectX.models.User;

@Service
public class CommentServiceImplementation implements CommentService{

	@Autowired
	PostService postService;
	
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	
	@Override
	public Comment createComment(Comment comment, Integer postId, Integer userId) throws Exception {
		User user=userService.findUserById(userId);
		
		Post post=postService.findPostById(postId);
		
		comment.setUser(user);
		comment.setContent(comment.getContent());
		comment.setCreatedAt(LocalDateTime.now());
		
		Comment savedComment=commentRepository.save(comment);
		
		post.getComments().add(savedComment);
		
		postRepository.save(post);
		return savedComment;
	}

	@Override
	public Comment findCommentById(Integer commentId) throws Exception {
		Optional<Comment> opt=commentRepository.findById(commentId);
		if(opt.isEmpty())
		{
			throw new Exception ("Comment not exist");
		}
		// TODO Auto-generated method stub
		return opt.get();
	}

	@Override
	public Comment likeComment(Integer CommentId, Integer userId) throws Exception {
		
		Comment comment=findCommentById(CommentId);
		
		User user=userService.findUserById(userId);
		if(comment.getLiked().contains(comment))
		{
			comment.getLiked().remove(user);
		}
		else comment.getLiked().add(user);
		// TODO Auto-generated method stub
		return commentRepository.save(comment);
	}

}
