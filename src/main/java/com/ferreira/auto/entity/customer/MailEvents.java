package com.ferreira.auto.entity.customer;

import com.ferreira.auto.entity.Customer;
import com.ferreira.auto.service.CustomerService;
import org.springframework.context.ApplicationEvent;

public class MailEvents extends ApplicationEvent {

    private Customer customer;
    private String subject;
    private String templateName;
    private String logo;

    public MailEvents(Object source, Customer customer, String templateName, String subject, String logo) {
        super(source);
        this.customer = customer;
        this.templateName = templateName;
        this.subject = subject;
        this.logo = logo;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
