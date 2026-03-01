package com.ferreira.auto.service.impl;

import com.ferreira.auto.dto.PrerentDto;
import com.ferreira.auto.dto.PrerentResponseDto;
import com.ferreira.auto.entity.Customer;
import com.ferreira.auto.entity.Model;
import com.ferreira.auto.entity.Prerent;
import com.ferreira.auto.entity.StatusPrerent;
import com.ferreira.auto.infra.configuration.MessageInternationalization;
import com.ferreira.auto.repository.PrerentRepository;
import com.ferreira.auto.service.CustomerService;
import com.ferreira.auto.service.ModelService;
import com.ferreira.auto.service.PrerentService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrerentServiceImpl implements PrerentService {

    @Autowired
    private PrerentRepository prerentRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ModelService modelService;

    @Autowired
    private MessageInternationalization messageInternationalization;

    @Override
    public PrerentResponseDto save(PrerentDto prerentDto) {

        Customer customer = customerService.getById(prerentDto.getCustomerId());
        Model model = modelService.getById(prerentDto.getModelId());

        Prerent prerent = new Prerent();
        prerent.setCustomer(customer);
        prerent.setModel(model);
        prerent.setStatus(StatusPrerent.IS_OPEN);

        Prerent prerentResult = prerentRepository.save(prerent);

        Optional<Prerent> optionalPrerent = Optional.ofNullable(prerentResult);
        PrerentResponseDto prerentResponseDto = new PrerentResponseDto(
                messageInternationalization.getMessage( "prerent.add.car"),
                "200",
                optionalPrerent);

        return prerentResponseDto;
    }

    @Override
    public List<Prerent> findByCustomerId(Long customerId) {
        return prerentRepository.findByCustomerId(customerId);
    }

    @Override
    @Transactional
    public void deleteByModelId(Long modelId) {
        prerentRepository.deleteByModelId(modelId);
    }

    @Override
    public boolean validateDataPrerent(PrerentDto prerentDto) {
        Prerent prerent = prerentRepository.findByCustomerIdAndModelId(prerentDto.getCustomerId(),
                prerentDto.getModelId());

        return prerent != null;
    }

    @Override
    public Long getQtyPrerent(Long customerId) {
        return prerentRepository.findQtyPrerentByCustomer(customerId);
    }
}
