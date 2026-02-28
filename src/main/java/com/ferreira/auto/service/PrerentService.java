package com.ferreira.auto.service;

import com.ferreira.auto.dto.PrerentDto;
import com.ferreira.auto.entity.Prerent;

public interface PrerentService {

    Prerent save(PrerentDto prerentDto);
    Prerent findByCustomerId(Long customerId);
    void deleteByModelId(Long modelId);
}
