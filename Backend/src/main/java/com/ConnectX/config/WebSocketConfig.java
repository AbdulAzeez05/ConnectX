package com.ConnectX.config;

import java.util.Arrays;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		String originsEnv = System.getenv().getOrDefault("CORS_ORIGINS", "http://localhost:3000");
		String[] origins = Arrays.stream(originsEnv.split(","))
				.map(String::trim)
				.map(o -> o.endsWith("/") ? o.substring(0, o.length() - 1) : o)
				.filter(o -> !o.isEmpty())
				.toArray(String[]::new);

		registry.addEndpoint("/ws")
				.setAllowedOriginPatterns(origins)
				.withSockJS();
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.setApplicationDestinationPrefixes("/app");   // client -> server
		registry.enableSimpleBroker("/topic");                // server -> clients
	}
}