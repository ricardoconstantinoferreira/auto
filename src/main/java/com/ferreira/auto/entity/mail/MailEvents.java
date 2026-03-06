package com.ferreira.auto.entity.mail;

import com.ferreira.auto.entity.Customer;
import org.springframework.context.ApplicationEvent;

public class MailEvents extends ApplicationEvent {

    private Customer customer;
    private String subject;
    private String templateName;
    private String logo;
    private Long orderId;
    private String type;

    public MailEvents(Object source) {
        super(source);
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

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
