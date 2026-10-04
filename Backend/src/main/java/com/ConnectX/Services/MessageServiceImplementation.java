package com.ConnectX.Services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ConnectX.UserRepository.ChatRepository;
import com.ConnectX.UserRepository.MessageRepository;
import com.ConnectX.models.Chat;
import com.ConnectX.models.Message;
import com.ConnectX.models.User;

@Service
public class MessageServiceImplementation implements MessageService{
	
	@Autowired
	private MessageRepository messageRepository;
	
	@Autowired
	private ChatService chatService;
	
	@Autowired
	private ChatRepository chatRepository;
	
	

	@Override
	public Message createMessage(User user, Integer ChatId, Message req) throws Exception {
		Message message =new Message();
		
		Chat chat=chatService.findChatById(ChatId);
		
		message.setChat(null);
		message.setContent(req.getContent());
		message.setImage(req.getImage());
		message.setUser(user);
		message.setTimestamp(LocalDateTime.now());
		
		Message savedMessage=messageRepository.save(message);
		
		chat.getMessages().add(savedMessage);
		chatRepository.save(chat);
		
		
		return savedMessage;
	}

	@Override
	public List<Message> findChatMessage(Integer chatId) throws Exception {
		Chat chat=chatService.findChatById(chatId);
		
		return messageRepository.findByChatId(chatId);
	}

}
