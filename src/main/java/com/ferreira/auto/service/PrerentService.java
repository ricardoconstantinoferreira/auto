package com.ferreira.auto.service;

import com.ferreira.auto.dto.PrerentDto;
import com.ferreira.auto.dto.PrerentResponseDto;
import com.ferreira.auto.entity.Prerent;

import java.util.List;

public interface PrerentService {

    PrerentResponseDto save(PrerentDto prerentDto);
    List<Prerent> findByCustomerId(Long customerId);
    void deleteByModelId(Long modelId);
    boolean validateDataPrerent(PrerentDto prerentDto);
    Long getQtyPrerent(Long customerId);
}
