package com.example.demo.controller;

import com.example.demo.dto.ChatRequest;
import com.example.demo.rag.RagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rag")
@RequiredArgsConstructor
public class RagController {

    private final RagService ragService;

    @PostMapping
    public String askQuestion(@RequestBody ChatRequest request) {

        return ragService.askQuestion(request.getQuery());
    }
}