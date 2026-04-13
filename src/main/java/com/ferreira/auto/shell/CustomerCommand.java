package com.ferreira.auto.shell;

import com.ferreira.auto.entity.Customer;
import com.ferreira.auto.entity.CustomerType;
import com.ferreira.auto.infra.configuration.MessageInternationalization;
import com.ferreira.auto.repository.CustomerRepository;
import com.ferreira.auto.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class CustomerCommand {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private MessageInternationalization messageInternationalization;

    @ShellMethod(value = "Add customer admin", key = "add")
    public void add(@ShellOption(value = {"--name", "-n"}) String name,
                    @ShellOption(value = {"--document", "-d"}) String document,
                    @ShellOption(value = {"--email", "-e"}) String email
    ) {
        Customer customer = new Customer();
        customer.setName(name);
        customer.setDocument(document);
        customer.setEmail(email);
        customer.setCustomerType(CustomerType.USER);
        customer.setActive(true);

        Customer entity = customerRepository.save(customer);

        customerService.sendMailCustomer(entity,
                messageInternationalization.getMessage("subject.password.message"));
    }
}
