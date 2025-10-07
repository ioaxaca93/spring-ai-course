package com.ioaxaca93.open.ai.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class StreamController {
    private final ChatClient chatClient;



    @GetMapping("/test")
    public Flux<String> test(){
        return chatClient.prompt().
                user("I have taken 5 leaves this year, how many leaves left?").stream().content();
    }
}
