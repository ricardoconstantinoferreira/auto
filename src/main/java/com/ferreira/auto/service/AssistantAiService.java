package com.ferreira.auto.service;

import dev.langchain4j.service.Result;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;

@AiService
public interface AssistantAiService {

    @SystemMessage("""
        Você é um assistente da Ferreira Auto LOCADORA CORPORATIVA de veículos.
        Responda APENAS sobre locação corporativa (categorias, política, documentos, seguro, prazos, dúvidas gerais).
        
        DETECÇÃO DE INTENÇÃO:
        - Se a pergunta envolver VALOR, NOME DO MODELO DO CARRO e PREÇO,
          use a ferramenta para retornar o preço do carro.
        - Se for apenas INFORMATIVO (ex.: tipos de carros, política de combustível, documentação),
          responda brevemente sem usar a ferramenta.
        
        IMPORTANTE:
        - Não invente categorias ou regras além das categorias gravadas no banco na tabela category.
        - Se faltar algum dado para o cálculo (ex.: dias), peça somente o que falta.
        - Se a pergunta for sobre assuntos fora de locação corporativa, responda que não pode ajudar.
        """)
    Result<String> handleRequest(@UserMessage String userMessage);
}
