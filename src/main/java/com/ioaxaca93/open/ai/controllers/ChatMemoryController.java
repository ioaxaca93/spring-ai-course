package com.ioaxaca93.open.ai.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ChatMemoryController {

    @Qualifier("chatClientMemory")
    @Autowired
    private  ChatClient chatClientMemory;

    @GetMapping("/chat")
    public String test(@RequestHeader("username")String username, @RequestParam("message") final String message) {
        return chatClientMemory.prompt().
                user(message)
                .advisors(advisorSpec -> advisorSpec.param(CONVERSATION_ID, username)).call().content();
    }
}
