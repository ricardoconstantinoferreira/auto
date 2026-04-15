package com.ferreira.auto.controller;

import com.ferreira.auto.dto.AssistantResponseDto;
import com.ferreira.auto.service.AssistantAiService;
import com.ferreira.auto.service.AssistantRentAiService;
import dev.langchain4j.service.Result;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/auto/assistent")
public class AssistantController {

    private final AssistantAiService assistantAiService;
    private final AssistantRentAiService assistantRentAiService;

    public AssistantController(
            @Qualifier("assistant") AssistantAiService assistantAiService,
            @Qualifier("assistantRent") AssistantRentAiService assistantRentAiService
    ) {
        this.assistantAiService = assistantAiService;
        this.assistantRentAiService = assistantRentAiService;
    }

    @PostMapping("/ask")
    public ResponseEntity<AssistantResponseDto> askAssistant(@RequestBody String userMessage) {
        Result<String> result = assistantAiService.handleRequest(userMessage);
        AssistantResponseDto assistantResponseDto = new AssistantResponseDto(result.content(), "201");

        return  ResponseEntity.status(HttpStatus.CREATED).body(assistantResponseDto);
    }

    @PostMapping("/rent")
    public ResponseEntity<AssistantResponseDto> askRent(@RequestBody String userMessage) {

        Result<String> result = assistantRentAiService.handleOrder(userMessage);
        AssistantResponseDto assistantResponseDto = new AssistantResponseDto(result.content(), "201");

        return  ResponseEntity.status(HttpStatus.CREATED).body(assistantResponseDto);
    }
}
