package org.example.configs;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ChatConfig {
    @Bean
    ChatClient chatClient(ChatModel model) {
        return ChatClient.create(model);
    }
}
