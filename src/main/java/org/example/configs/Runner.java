package org.example.configs;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnBean(ChatClient.class)
public class Runner implements CommandLineRunner {
    private final ChatClient chatClient;

    public Runner(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @Override
    public void run(String... args) {
        try {
            var response = chatClient.prompt("Tell me a joke about Java").call().content();
            System.out.println("AI says: " + response);
        } catch (Exception e) {
            e.printStackTrace(); // log so startup doesn't crash
        }
    }
}
