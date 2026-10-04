package com.ConnectX.Services;

import java.util.List;

import com.ConnectX.exceptions.StoryException;
import com.ConnectX.exceptions.UserException;
import com.ConnectX.models.Story;

public interface StoryService {
public Story createStory(Story story,Integer userId) throws UserException;
	
	public List<Story> findStoryByUserId(Integer userId) throws UserException, StoryException;
	

}
