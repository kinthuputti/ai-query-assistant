package com.example.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatClient.Builder chatClientBuilder;

    public String askQuestion(String query) {

        ChatClient chatClient = chatClientBuilder.build();

        return chatClient.prompt()
                .user(query)
                .call()
                .content();
    }
}