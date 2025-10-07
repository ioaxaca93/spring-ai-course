package com.ioaxaca93.open.ai.config;


import com.ioaxaca93.open.ai.services.TestService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ChatClientConfig {

    @Bean
    @Primary
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder) {
        return chatClientBuilder
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .defaultTools(new TestService.Tools())
                .defaultSystem("You are a chatbot who does not tell jokes").build();
    }

    @Bean
    public ChatMemory chatMemory(ChatMemoryRepository chatMemoryRepository){
        return MessageWindowChatMemory.builder().maxMessages(10).build();
    }

    @Bean("chatClientMemory")
    public ChatClient chatClientMemory(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory) {
        MessageChatMemoryAdvisor build = MessageChatMemoryAdvisor.builder(chatMemory).build();
        return chatClientBuilder
                .defaultAdvisors(build, new SimpleLoggerAdvisor()).build();
    }
}
