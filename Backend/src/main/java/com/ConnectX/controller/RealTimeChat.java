package com.ConnectX.controller;

import java.util.Map;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class RealTimeChat {

	private final SimpMessagingTemplate template;

	public RealTimeChat(SimpMessagingTemplate template) {
		this.template = template;
	}

	// Client sends to /app/chat/{chatId}; everyone subscribed to /topic/chat/{chatId} receives it
@MessageMapping("/chat/{chatId}")
public void send(@DestinationVariable String chatId, @Payload Map<String, Object> message) {
	template.convertAndSend("/topic/chat/" + chatId, (Object) message);
}
}