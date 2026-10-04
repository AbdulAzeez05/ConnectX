package com.ConnectX.Services;

import java.util.List;

import com.ConnectX.models.Reels;
import com.ConnectX.models.User;

public interface ReelsService {
	public Reels createReel(Reels reel ,User user);
	
	public List<Reels> findAllReels();
	
	public List<Reels> findUsersReel(Integer userId) throws Exception ;
		

}
