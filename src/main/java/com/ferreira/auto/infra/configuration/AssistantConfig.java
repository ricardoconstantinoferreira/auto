package com.ferreira.auto.infra.configuration;

import com.ferreira.auto.service.AssistantAiService;
import com.ferreira.auto.service.AssistantRentAiService;
import com.ferreira.auto.tools.AssistantRentTools;
import com.ferreira.auto.tools.AssistantTools;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.service.AiServices;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AssistantConfig {

    @Value("${langchain4j.ollama.chat-model.model-name:llama3.1}")
    private String ollamaModel;

    @Value("${langchain4j.ollama.chat-model.base-url:http://localhost:11434}")
    private String ollamaUrl;

    @Bean
    public OllamaChatModel ollamaChatModel() {
        return OllamaChatModel.builder()
                .baseUrl(ollamaUrl)
                .modelName(ollamaModel)
                .build();
    }

    @Bean("assistant")
    public AssistantAiService assistant(OllamaChatModel model, AssistantTools assistantTools) {
        return AiServices.builder(AssistantAiService.class)
                .chatLanguageModel(model)
                .tools(assistantTools)
                .build();
    }

    @Bean("assistantRent")
    public AssistantRentAiService assistantRent(OllamaChatModel model, AssistantRentTools assistantRentTools) {
        return AiServices.builder(AssistantRentAiService.class)
                .chatLanguageModel(model)
                .tools(assistantRentTools)
                .build();
    }

}
