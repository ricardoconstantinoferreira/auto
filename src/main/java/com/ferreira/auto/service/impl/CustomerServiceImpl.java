package com.ferreira.auto.service.impl;

import com.ferreira.auto.dto.CustomerDto;
import com.ferreira.auto.dto.ResetPasswordDto;
import com.ferreira.auto.dto.UpdatedPasswordDto;
import com.ferreira.auto.entity.Customer;
import com.ferreira.auto.entity.mail.MailEvents;
import com.ferreira.auto.infra.configuration.MessageInternationalization;
import com.ferreira.auto.publisher.customer.SendMailPublisher;
import com.ferreira.auto.repository.CustomerRepository;
import com.ferreira.auto.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SendMailPublisher sendMail;

    @Autowired
    private MessageInternationalization messageInternationalization;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Customer save(CustomerDto customerDto) {

        Customer customer = new Customer();
        customer.setName(customerDto.getName());
        customer.setDocument(customerDto.getDocument());
        customer.setCustomerType(customerDto.getCustomerType());
        customer.setEmail(customerDto.getEmail());

        if (customerDto.getId() == null) {
            customer.setActive(true);
        } else {
            customer.setId(customerDto.getId());
            customer.setActive(customerDto.isActive());
        }

        return customerRepository.save(customer);
    }

    @Override
    public Customer update(Long customerId, CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setId(customerId);
        customer.setName(customerDto.getName());
        customer.setDocument(customerDto.getDocument());
        customer.setEmail(customerDto.getEmail());

        Customer customer1 = this.getById(customerId);

        customer.setCustomerType(customer1.getCustomerType());
        customer.setPassword(customer1.getPassword());
        customer.setToken(customer1.getToken());
        customer.setActive(customer1.isActive());

        return customerRepository.save(customer);
    }

    @Override
    public Customer getById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(messageInternationalization.getMessage("customer.not.find")));

        return customer;
    }

    @Override
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    @Override
    public boolean delete(Long id) {
        Customer customer = this.getById(id);

        if (customer != null) {
            customer.setActive(false);
            customer = customerRepository.save(customer);
        }

        return customer.isActive();
    }

    @Override
    public void saveToken(Customer customer, String token) {
        customer.setToken(token);
        customerRepository.save(customer);
    }

    @Override
    public Customer getCustomerByEmail(String email) {
        Optional<Customer> customerOptional = customerRepository.findByEmail(email);

        if (customerOptional.isEmpty()) {
            return null;
        }

        return customerRepository.findByEmail(email).get();
    }

    @Override
    public boolean hasCustomerByEmail(CustomerDto customerDto) {
        Optional<Customer> customerOptional = customerRepository.findByEmail(customerDto.getEmail());
        return !customerOptional.isEmpty();
    }

    @Override
    public boolean hasCustomerByDocument(CustomerDto customerDto) {
        Optional<Customer> customerDocumentOptional = customerRepository.findByDocument(customerDto.getDocument());
        return !customerDocumentOptional.isEmpty();
    }

    @Override
    public void sendMailCustomer(Customer customer, String subject) {
        String templateName = "reset";
        String logo = "templates/images/ferrieira-auto.png";

        MailEvents mailEvents = new MailEvents(this);
        mailEvents.setCustomer(customer);
        mailEvents.setSubject(subject);
        mailEvents.setTemplateName(templateName);
        mailEvents.setLogo(logo);
        mailEvents.setType("mailStrategyCustomer");

        sendMail.doSendMail(mailEvents);
    }

    @Override
    public boolean resetPassword(ResetPasswordDto resetPasswordDto, Long id) {
        String password = passwordEncoder.encode(resetPasswordDto.getPassword());
        Customer customer = this.getById(id);

        customer.setPassword(password);
        Customer entity = customerRepository.save(customer);
        return entity != null;
    }

    @Override
    public void updatedPassword(Long customerId, UpdatedPasswordDto updatedPasswordDto) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException(messageInternationalization.getMessage("customer.not.find")));

        if (!updatedPasswordDto.getNewPassword().equals(updatedPasswordDto.getConfirmPassword())) {
            throw new RuntimeException(messageInternationalization.getMessage("customer.password.not.equals"));
        }

        if (!passwordEncoder.matches(updatedPasswordDto.getCurrentPassword(), customer.getPassword())) {
            throw new RuntimeException(messageInternationalization.getMessage("customer.password.not.correct"));
        }

        customer.setPassword(passwordEncoder.encode(updatedPasswordDto.getNewPassword()));
        customerRepository.save(customer);
    }

}
