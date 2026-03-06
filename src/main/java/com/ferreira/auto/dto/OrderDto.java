package com.ferreira.auto.dto;

import java.io.Serializable;
import java.util.List;

public class OrderDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long customerId;

    private List<ItemsDto> itemsDto;

    public OrderDto() {}

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public List<ItemsDto> getItemsDto() {
        return itemsDto;
    }

    public void setItemsDto(List<ItemsDto> itemsDto) {
        this.itemsDto = itemsDto;
    }
}
