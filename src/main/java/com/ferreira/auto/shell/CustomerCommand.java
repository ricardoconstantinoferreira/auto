package com.ferreira.auto.shell;

import com.ferreira.auto.dto.CustomerDto;
import com.ferreira.auto.entity.Customer;
import com.ferreira.auto.entity.CustomerType;
import com.ferreira.auto.infra.configuration.MessageInternationalization;
import com.ferreira.auto.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class CustomerCommand {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private MessageInternationalization messageInternationalization;

    @ShellMethod(value = "Add customer admin", key = "add")
    public void add(@ShellOption(value = {"--name", "-n"}) String name,
                    @ShellOption(value = {"--document", "-d"}) String document,
                    @ShellOption(value = {"--email", "-e"}) String email
    ) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setName(name);
        customerDto.setDocument(document);
        customerDto.setEmail(email);
        customerDto.setCustomerType(CustomerType.USER);
        customerDto.setActive(true);

        Customer entity = customerService.save(customerDto);

        customerService.sendMailCustomer(entity,
                messageInternationalization.getMessage("subject.password.message"));
    }
}
