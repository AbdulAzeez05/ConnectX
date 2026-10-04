package com.ConnectX.UserRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ConnectX.models.Post;
import java.util.List;

	public interface PostRepository extends JpaRepository<Post, Integer> {

	    @Query("select p from Post p where p.user.id = :userId")
	    List<Post> findPostByUserId(@Param("userId") Integer userId);

	}

