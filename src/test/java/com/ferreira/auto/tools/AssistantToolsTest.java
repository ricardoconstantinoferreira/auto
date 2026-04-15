package com.ferreira.auto.tools;

import com.ferreira.auto.entity.Model;
import com.ferreira.auto.repository.ModelRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AssistantToolsTest {

    @Mock
    private ModelRepository modelRepository;

    @InjectMocks
    private AssistantTools tools;

    @Test
    void getPriceCarReturnsFormattedPrice() {
        Model model = new Model();
        model.setId(5L);
        model.setDescription("Gurgel");
        model.setPrice(12345.67f);

        when(modelRepository.findByDescriptionIgnoreCase("Gurgel")).thenReturn(Optional.of(model));

        String result = tools.getPriceCar("Gurgel");

        assertTrue(result.contains("Gurgel"));
        assertTrue(result.contains("12345"));
    }
}
