package com.ferreira.auto.controller;

import com.ferreira.auto.dto.CustomerDto;
import com.ferreira.auto.dto.CustomerResponseDto;
import com.ferreira.auto.dto.ResetPasswordDto;
import com.ferreira.auto.entity.Customer;
import com.ferreira.auto.entity.CustomerType;
import com.ferreira.auto.exception.CustomerAlreadyExistsException;
import com.ferreira.auto.infra.configuration.MessageInternationalization;
import com.ferreira.auto.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @Mock
    private MessageInternationalization messageInternationalization;

    @InjectMocks
    private CustomerController controller;

    @Test
    void saveThrowsWhenEmailExists() {
        CustomerDto dto = new CustomerDto();
        dto.setEmail("e@x.com");

        when(customerService.hasCustomerByEmail(dto)).thenReturn(true);
        when(messageInternationalization.getMessage("customer.exists.message")).thenReturn("exists");

        assertThrows(CustomerAlreadyExistsException.class, () -> controller.save(dto));
    }

    @Test
    void saveThrowsWhenDocumentExists() {
        CustomerDto dto = new CustomerDto();
        dto.setDocument("doc");

        when(customerService.hasCustomerByEmail(dto)).thenReturn(false);
        when(customerService.hasCustomerByDocument(dto)).thenReturn(true);
        when(messageInternationalization.getMessage("customer.exists.message")).thenReturn("exists");

        assertThrows(CustomerAlreadyExistsException.class, () -> controller.save(dto));
    }

    @Test
    void saveReturnsCreatedAndSendsMail() {
        CustomerDto dto = new CustomerDto();
        dto.setName("John");
        dto.setDocument("123");
        dto.setCustomerType(CustomerType.USER);
        dto.setEmail("j@x.com");

        Customer customer = new Customer();
        customer.setEmail("j@x.com");

        when(customerService.hasCustomerByEmail(dto)).thenReturn(false);
        when(customerService.hasCustomerByDocument(dto)).thenReturn(false);
        when(customerService.save(dto)).thenReturn(customer);
        when(messageInternationalization.getMessage("customer.save.message")).thenReturn("saved");
        when(messageInternationalization.getMessage("subject.password.message")).thenReturn("subject");

        ResponseEntity<CustomerResponseDto> response = controller.save(dto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("saved", response.getBody().message());
        verify(customerService).sendMailCustomer(customer, "subject");
    }

    @Test
    void resetPasswordReturnsNotFoundWhenMismatch() {
        ResetPasswordDto dto = new ResetPasswordDto();
        dto.setPassword("a");
        dto.setConfirmPassword("b");
        when(messageInternationalization.getMessage("password.no.equal")).thenReturn("diff");

        ResponseEntity<String> response = controller.resetPassword(1L, dto);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("diff", response.getBody());
    }

    @Test
    void resetPasswordReturnsOkWhenServiceReturnsTrue() {
        ResetPasswordDto dto = new ResetPasswordDto();
        dto.setPassword("a");
        dto.setConfirmPassword("a");

        when(messageInternationalization.getMessage("password.reset.invalid")).thenReturn("invalid");
        when(messageInternationalization.getMessage("password.reset.valid")).thenReturn("valid");
        when(customerService.resetPassword(dto, 1L)).thenReturn(true);

        ResponseEntity<String> response = controller.resetPassword(1L, dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("valid", response.getBody());
    }

    @Test
    void resetPasswordReturnsNotFoundWhenServiceReturnsFalse() {
        ResetPasswordDto dto = new ResetPasswordDto();
        dto.setPassword("a");
        dto.setConfirmPassword("a");

        when(messageInternationalization.getMessage("password.reset.invalid")).thenReturn("invalid");
        when(customerService.resetPassword(dto, 1L)).thenReturn(false);

        ResponseEntity<String> response = controller.resetPassword(1L, dto);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("invalid", response.getBody());
    }

    @Test
    void getByIdReturnsOk() {
        Customer customer = new Customer();
        when(customerService.getById(1L)).thenReturn(customer);

        ResponseEntity<Customer> response = controller.getById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertSame(customer, response.getBody());
    }

    @Test
    void getAllReturnsOk() {
        when(customerService.getAll()).thenReturn(List.of(new Customer()));

        ResponseEntity<List<Customer>> response = controller.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void deleteReturnsOkWhenInactive() {
        when(customerService.delete(1L)).thenReturn(false);
        when(messageInternationalization.getMessage("customer.deleted.message")).thenReturn("deleted");

        ResponseEntity<String> response = controller.delete(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("deleted", response.getBody());
    }

    @Test
    void deleteReturnsNotFoundWhenStillActive() {
        when(customerService.delete(1L)).thenReturn(true);
        when(messageInternationalization.getMessage("customer.no.deleted.message")).thenReturn("not deleted");

        ResponseEntity<String> response = controller.delete(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("not deleted", response.getBody());
    }
}
