package com.ferreira.auto.tools;

import com.ferreira.auto.dto.ItemsDto;
import com.ferreira.auto.dto.OrderDto;
import com.ferreira.auto.dto.OrderResponseDto;
import com.ferreira.auto.entity.Customer;
import com.ferreira.auto.entity.Model;
import com.ferreira.auto.infra.validator.OrderValidator;
import com.ferreira.auto.repository.ModelRepository;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AssistantRentTools {

    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private OrderValidator orderValidator;

    @Tool("Realiza o pedido de aluguel do veículo.")
    public String rentModel(String modelName) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Long customerId = ((Customer) ((UsernamePasswordAuthenticationToken) auth).getPrincipal()).getId();

        Model model = modelRepository.findByDescriptionIgnoreCase(modelName)
                .orElseThrow(() -> new RuntimeException("Carro não encontrado"));

        OrderDto orderDto = getOrderDto(model, customerId);
        OrderResponseDto responseDto = orderValidator.validator(orderDto);

        return responseDto.message();
    }

    private OrderDto getOrderDto(Model model, Long customerId) {
        List arrayListItems = new ArrayList<>();
        ItemsDto itemsDto = new ItemsDto();
        itemsDto.setModelId(model.getId());
        arrayListItems.add(itemsDto);

        OrderDto orderDto = new OrderDto();
        orderDto.setCustomerId(customerId);
        orderDto.setItemsDto(arrayListItems);

        return orderDto;
    }
}
