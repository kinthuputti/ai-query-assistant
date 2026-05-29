package com.example.demo.controller;

import com.example.demo.service.StreamingChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/stream")
@RequiredArgsConstructor
public class StreamingChatController {

    private final StreamingChatService streamingChatService;

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> stream(@RequestParam String query) {

        return streamingChatService.streamResponse(query);
    }
}