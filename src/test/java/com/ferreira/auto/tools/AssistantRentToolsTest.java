package com.ferreira.auto.tools;

import com.ferreira.auto.dto.OrderResponseDto;
import com.ferreira.auto.entity.Customer;
import com.ferreira.auto.entity.Model;
import com.ferreira.auto.infra.validator.OrderValidator;
import com.ferreira.auto.repository.ModelRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AssistantRentToolsTest {

    @Mock
    private ModelRepository modelRepository;

    @Mock
    private OrderValidator orderValidator;

    @InjectMocks
    private AssistantRentTools tools;

    @AfterEach
    void clear() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void rentModelUsesOrderValidatorAndReturnsMessage() {
        Model model = new Model();
        model.setId(7L);
        model.setDescription("Gurgel");
        model.setPrice(1000f);

        Customer customer = new Customer();
        customer.setId(3L);

        when(modelRepository.findByDescriptionIgnoreCase("Gurgel")).thenReturn(Optional.of(model));

        OrderResponseDto responseDto = new OrderResponseDto("pedido criado", "201", null);
        when(orderValidator.validator(org.mockito.ArgumentMatchers.any())).thenReturn(responseDto);

        // configurar autenticação com principal Customer
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(customer, null);
        SecurityContextHolder.getContext().setAuthentication(auth);

        String result = tools.rentModel("Gurgel");

        assertEquals("pedido criado", result);
    }
}
