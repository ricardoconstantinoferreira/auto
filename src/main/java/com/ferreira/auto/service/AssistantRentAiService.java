package com.ferreira.auto.service;

import dev.langchain4j.service.Result;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;

@AiService
public interface AssistantRentAiService {

    @SystemMessage("""
        Você é um assistente da Ferreira Auto LOCADORA CORPORATIVA de veículos.
        Responda APENAS sobre o aluguel do veículo.
        
        DETECÇÃO DE INTENÇÃO:
        - Se frases afirmativas e parecidas com quero alugar o carro tal, pegar esse carro e realizar o aluguel dele.
        
        IMPORTANTE:
        - Os carros devem ser apenas os que estão na tabela model
        - Se o método modelRepository.findByDescriptionIgnoreCase trouxer dados, é porque tem carro na tabela
        - Se o carro não estiver na tabela de model, responda que não pode ajudar.
        """)
    Result<String> handleOrder(@UserMessage String userMessage);
}
