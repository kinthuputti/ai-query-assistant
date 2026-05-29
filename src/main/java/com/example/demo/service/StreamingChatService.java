package com.example.demo.service;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
@Service
@RequiredArgsConstructor
public class StreamingChatService {
    private final ChatClient.Builder chatClientBuilder;
    public Flux<String> streamResponse(String query) {
        ChatClient chatClient = chatClientBuilder.build();
        return chatClient.prompt()
                .user(query)
                .stream()
                .content();
    }
}