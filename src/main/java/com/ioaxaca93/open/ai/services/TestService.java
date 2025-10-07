package com.ioaxaca93.open.ai.services;

import com.ioaxaca93.open.ai.model.ActorFilms;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class TestService {

    private final ChatClient chatClient;
    @Value("classpath:/prompts/user-message.st")
    private Resource promptUserTemplate;


   // @EventListener(ApplicationReadyEvent.class)
    public void listen() {
        System.out.println("Asking for a filmography");
        System.out.println(chatClient.prompt()
                        .user("Generate the filmography for a random actor.")
                .call().entity(ActorFilms.class));
        System.out.println("Asking for a filmography end");
    }

   // @EventListener(ApplicationReadyEvent.class)
    public void joke() {
        System.out.println("Asking for a joke");
        System.out.println(chatClient.prompt()
                .user(promptUserSpec ->
                        promptUserSpec.text(promptUserTemplate).param("customerMessage","Tell me a funny joke")
                                .param("customerName","Israel"))
                .call().content());
        System.out.println("Asking for a joke end");
    }

   // @EventListener(ApplicationReadyEvent.class)
    public void whatTimeIs() {
        System.out.println("Asking for the time");
        System.out.println(chatClient.prompt()
                .user("What time is and day is today?")
                .call().content());
        System.out.println("Asking for the time");
    }

    public static class Tools{
        @Tool(description = "Get the current date and time in the user's timezone")
        public String getCurrentDateTime() {
            return LocalDateTime.now().atZone(LocaleContextHolder.getTimeZone().toZoneId()).toString();
        }
    }

}
