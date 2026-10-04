package com.ConnectX.Services;

import java.util.List;

import com.ConnectX.models.Chat;
import com.ConnectX.models.Message;
import com.ConnectX.models.User;

public interface MessageService {
	
	public Message createMessage(User user,Integer ChatId,Message req) throws Exception;
	
	public List<Message> findChatMessage(Integer chatId) throws Exception;

}
