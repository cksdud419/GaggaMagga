package com.ssafy.project.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatConfig {
	@Bean
	ChatClient simpleChatClient(ChatClient.Builder builder) {
		return builder.build();
	}
}
