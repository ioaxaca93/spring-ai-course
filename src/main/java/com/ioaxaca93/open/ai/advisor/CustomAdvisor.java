package com.ioaxaca93.open.ai.advisor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.ai.chat.model.ChatResponse;

@Slf4j
public class CustomAdvisor implements CallAdvisor {
    @Override
    public ChatClientResponse adviseCall(ChatClientRequest chatClientRequest, CallAdvisorChain callAdvisorChain) {
        ChatClientResponse chatClientResponse = callAdvisorChain.nextCall(chatClientRequest);
        ChatResponse chatResponse = chatClientResponse.chatResponse();
        if (chatResponse.getMetadata()!=null){
            log.info("Custom advisor usage response: {}", chatResponse.getMetadata().getUsage());
        }
        return chatClientResponse;
    }

    @Override
    public String getName() {
        return "CustomAdvisor";
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
