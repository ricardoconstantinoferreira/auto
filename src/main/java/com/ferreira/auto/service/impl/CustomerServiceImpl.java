package com.ferreira.auto.service.impl;

import com.ferreira.auto.dto.CustomerDto;
import com.ferreira.auto.dto.ResetPasswordDto;
import com.ferreira.auto.entity.Customer;
import com.ferreira.auto.publisher.customer.SendMailPublisher;
import com.ferreira.auto.repository.CustomerRepository;
import com.ferreira.auto.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SendMailPublisher sendMail;

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
    public Customer getById(Long id) {
        return customerRepository.getReferenceById(id);
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

        sendMail.doSendMail(customer, templateName, logo, subject);
    }

    @Override
    public boolean resetPassword(ResetPasswordDto resetPasswordDto, Long id) {
        String password = new BCryptPasswordEncoder().encode(resetPasswordDto.getPassword());
        Customer customer = this.getById(id);

        customer.setPassword(password);
        Customer entity = customerRepository.save(customer);
        return entity != null;
    }

}
