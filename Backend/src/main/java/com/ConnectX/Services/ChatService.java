package com.ConnectX.Services;

import java.util.List;

import com.ConnectX.models.Chat;
import com.ConnectX.models.User;

public interface ChatService {
	
	public Chat createChat(User reqUser,User user2);
	
	public Chat findChatById(Integer chatId) throws Exception;
	public List<Chat> findUsersChat(Integer userId);
	

}
