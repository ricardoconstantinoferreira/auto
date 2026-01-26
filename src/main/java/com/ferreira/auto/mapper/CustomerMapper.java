package com.ferreira.auto.mapper;

import com.ferreira.auto.dto.CustomerDto;
import com.ferreira.auto.entity.Customer;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {
    CustomerDto toDto(Customer byId);
}
