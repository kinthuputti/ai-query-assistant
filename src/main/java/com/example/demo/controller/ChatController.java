package com.example.demo.controller;

import com.example.demo.dto.ChatRequest;
import com.example.demo.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping
    public String chat(@RequestBody ChatRequest request) {

        return chatService.askQuestion(request.getQuery());
    }
}