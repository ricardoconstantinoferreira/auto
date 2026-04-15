package com.ferreira.auto.controller;

import com.ferreira.auto.dto.AssistantResponseDto;
import com.ferreira.auto.service.AssistantAiService;
import com.ferreira.auto.service.AssistantRentAiService;
import dev.langchain4j.service.Result;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class AssistantControllerTest {

    @Mock
    private AssistantAiService assistantAiService;

    @Mock
    private AssistantRentAiService assistantRentAiService;

    @InjectMocks
    private AssistantController controller;

    @Test
    void askAssistantDelegatesToAssistantAiService() {
        Result<String> result = mock(Result.class);
        when(result.content()).thenReturn("resposta assistente");
        when(assistantAiService.handleRequest("qual o preco?"))
                .thenReturn(result);

        ResponseEntity<AssistantResponseDto> response = controller.askAssistant("qual o preco?");

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("resposta assistente", response.getBody().message());
    }

    @Test
    void askRentDelegatesToAssistantRentAiService() {
        Result<String> result = mock(Result.class);
        when(result.content()).thenReturn("resposta aluguel");
        when(assistantRentAiService.handleOrder("quero alugar"))
                .thenReturn(result);

        ResponseEntity<AssistantResponseDto> response = controller.askRent("quero alugar");

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("resposta aluguel", response.getBody().message());
    }
}
