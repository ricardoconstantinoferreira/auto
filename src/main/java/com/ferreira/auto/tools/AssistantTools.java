package com.ferreira.auto.tools;

import com.ferreira.auto.entity.Model;
import com.ferreira.auto.repository.ModelRepository;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AssistantTools {

    @Autowired
    private ModelRepository modelRepository;

    @Tool("Retorna o valor do preço do carro baseado no nome do modelo do veiculo.")
    public String getPriceCar(String carName) {
        Model model = modelRepository.findByDescriptionIgnoreCase(carName)
                .orElseThrow(() -> new RuntimeException(
                        "Carro não encontrado"
                ));

        return String.format(
                "Preço: do %s é %.2f",
                 carName, model.getPrice()
        );
    }
}
