package com.ioaxaca93.open.ai.services;

import com.ioaxaca93.open.ai.advisor.CustomAdvisor;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PromptStuffingService {
    private final ChatClient chatClient;

    @Value("classpath:/prompts/system-message.st")
    Resource promptSystemTemplate;


    //@EventListener(ApplicationReadyEvent.class)
    public void test(){
        System.out.println(chatClient.prompt().advisors(new CustomAdvisor()).system(promptSystemTemplate).user("I have taken 5 leaves this year, how many leaves left?").call().content());
    }
}
