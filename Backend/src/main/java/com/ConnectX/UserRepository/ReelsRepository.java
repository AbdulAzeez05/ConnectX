package com.ConnectX.UserRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ConnectX.models.Reels;

public interface ReelsRepository extends JpaRepository<Reels,Integer>{
	
	
	
	public List<Reels> findByUserId(Integer userId);
	

}
