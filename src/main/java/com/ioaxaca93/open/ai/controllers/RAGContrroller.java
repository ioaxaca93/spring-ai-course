package com.ioaxaca93.open.ai.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

@RestController
@RequestMapping("/api-rag")
@RequiredArgsConstructor
public class RAGContrroller {

    @Qualifier("chatClientMemory")
    @Autowired
    private ChatClient chatClient;
    @Autowired
    private VectorStore vectorStore;
    @Value("classpath:/prompts/system-rag-data.st")
    Resource promptSystemTemplate;
    @Value("classpath:/prompts/system-message-hr.st")
    Resource promptSystemHrTemplate;


    @GetMapping("/chat")
    public String test(@RequestHeader("username")String username, @RequestParam("message") final String message) {
        //SearchRequest searchRequest = SearchRequest.builder().query(message).topK(30).similarityThreshold(0.5).build();
        //List<Document> documents = vectorStore.similaritySearch(searchRequest);
        //String docs = documents.stream().map(Document::getText).collect(Collectors.joining(System.lineSeparator()));
        return chatClient.prompt().
                user(message)
                //.system(e->e.text(promptSystemTemplate).param("documents",docs))
                .advisors(advisorSpec -> advisorSpec.param(CONVERSATION_ID, username)).call().content();
    }

    @GetMapping("/chat-hr")
    public String testHr(@RequestHeader("username")String username, @RequestParam("message") final String message) {
        //SearchRequest searchRequest = SearchRequest.builder().query(message).topK(3).similarityThreshold(0.5).build();
        //List<Document> documents = vectorStore.similaritySearch(searchRequest);
        //String docs = documents.stream().map(Document::getText).collect(Collectors.joining(System.lineSeparator()));
        return chatClient.prompt().
                user(message)
          //      .system(e->e.text(promptSystemHrTemplate).param("documents",docs))
                .advisors(advisorSpec -> advisorSpec.param(CONVERSATION_ID, username)).call().content();
    }
}
