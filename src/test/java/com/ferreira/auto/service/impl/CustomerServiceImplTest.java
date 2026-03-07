package com.ferreira.auto.service.impl;

import com.ferreira.auto.dto.CustomerDto;
import com.ferreira.auto.dto.ResetPasswordDto;
import com.ferreira.auto.entity.Customer;
import com.ferreira.auto.entity.CustomerType;
import com.ferreira.auto.entity.mail.MailEvents;
import com.ferreira.auto.publisher.customer.SendMailPublisher;
import com.ferreira.auto.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private SendMailPublisher sendMail;

    @InjectMocks
    private CustomerServiceImpl service;

    @Test
    void saveSetsActiveTrueWhenNew() {
        CustomerDto dto = baseDto();
        dto.setId(null);
        when(customerRepository.save(any(Customer.class))).thenAnswer(i -> i.getArgument(0));

        Customer saved = service.save(dto);

        assertTrue(saved.isActive());
        assertNull(saved.getId());
    }

    @Test
    void saveUsesIncomingIdAndActiveWhenUpdate() {
        CustomerDto dto = baseDto();
        dto.setId(5L);
        dto.setActive(false);
        when(customerRepository.save(any(Customer.class))).thenAnswer(i -> i.getArgument(0));

        Customer saved = service.save(dto);

        assertEquals(5L, saved.getId());
        assertFalse(saved.isActive());
    }

    @Test
    void getByIdReturnsEntity() {
        Customer customer = new Customer();
        when(customerRepository.findById(2L)).thenReturn(Optional.of(customer));

        assertSame(customer, service.getById(2L));
    }

    @Test
    void getAllReturnsList() {
        when(customerRepository.findAll()).thenReturn(List.of(new Customer()));
        assertEquals(1, service.getAll().size());
    }

    @Test
    void deleteReturnsFalseWhenCustomerSavedInactive() {
        Customer customer = new Customer();
        customer.setActive(true);
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(customerRepository.save(any(Customer.class))).thenAnswer(i -> i.getArgument(0));

        boolean result = service.delete(1L);

        assertFalse(result);
    }

    @Test
    void saveTokenUpdatesAndPersists() {
        Customer customer = new Customer();
        service.saveToken(customer, "tok");
        assertEquals("tok", customer.getToken());
        verify(customerRepository).save(customer);
    }

    @Test
    void getCustomerByEmailReturnsNullWhenNotFound() {
        when(customerRepository.findByEmail("x@y")).thenReturn(Optional.empty());
        assertNull(service.getCustomerByEmail("x@y"));
    }

    @Test
    void getCustomerByEmailReturnsCustomerWhenFound() {
        Customer customer = new Customer();
        when(customerRepository.findByEmail("x@y")).thenReturn(Optional.of(customer));

        assertSame(customer, service.getCustomerByEmail("x@y"));
    }

    @Test
    void hasCustomerByEmailAndDocument() {
        CustomerDto dto = baseDto();
        when(customerRepository.findByEmail(dto.getEmail())).thenReturn(Optional.of(new Customer()));
        when(customerRepository.findByDocument(dto.getDocument())).thenReturn(Optional.empty());

        assertTrue(service.hasCustomerByEmail(dto));
        assertFalse(service.hasCustomerByDocument(dto));
    }

    @Test
    void sendMailCustomerBuildsEventAndPublishes() {
        Customer customer = new Customer();
        customer.setId(10L);
        customer.setName("Ana");

        service.sendMailCustomer(customer, "subject");

        ArgumentCaptor<MailEvents> captor = ArgumentCaptor.forClass(MailEvents.class);
        verify(sendMail).doSendMail(captor.capture());
        MailEvents event = captor.getValue();
        assertEquals("subject", event.getSubject());
        assertEquals("mailStrategyCustomer", event.getType());
        assertEquals("reset", event.getTemplateName());
    }

    @Test
    void resetPasswordReturnsTrueWhenSaveReturnsEntity() {
        Customer customer = new Customer();
        when(customerRepository.findById(7L)).thenReturn(Optional.of(customer));
        when(customerRepository.save(any(Customer.class))).thenAnswer(i -> i.getArgument(0));

        ResetPasswordDto dto = new ResetPasswordDto();
        dto.setPassword("123");

        assertTrue(service.resetPassword(dto, 7L));
        assertNotEquals("123", customer.getPassword());
    }

    private CustomerDto baseDto() {
        CustomerDto dto = new CustomerDto();
        dto.setName("Ana");
        dto.setDocument("123");
        dto.setCustomerType(CustomerType.USER);
        dto.setEmail("ana@mail.com");
        return dto;
    }
}
