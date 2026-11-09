package com.sisgestion_back.sigestion_back.controller;

import com.sisgestion_back.sigestion_back.service.RAGService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Rag")
public class RAGController {

    private final RAGService ragService;

    public RAGController(RAGService ragService) {
        this.ragService = ragService;
    }

    @GetMapping("/query")
    public ResponseEntity<String> askQuestion(@RequestParam String question) {
        String response = ragService.generateResponse(question);
        return ResponseEntity.ok(response);
    }
}