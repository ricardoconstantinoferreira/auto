package com.ferreira.auto.service.impl;

import com.ferreira.auto.dto.PrerentDto;
import com.ferreira.auto.entity.Customer;
import com.ferreira.auto.entity.Model;
import com.ferreira.auto.entity.Prerent;
import com.ferreira.auto.repository.PrerentRepository;
import com.ferreira.auto.service.CustomerService;
import com.ferreira.auto.service.ModelService;
import com.ferreira.auto.service.PrerentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PrerentServiceImpl implements PrerentService {

    @Autowired
    private PrerentRepository prerentRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ModelService modelService;

    @Override
    public Prerent save(PrerentDto prerentDto) {
        Customer customer = customerService.getById(prerentDto.getCustomer_id());
        Model model = modelService.getById(prerentDto.getModel_id());

        Prerent prerent = new Prerent();
        prerent.setCustomer(customer);
        prerent.setModel(model);

        return prerentRepository.save(prerent);
    }

    @Override
    public Prerent findByCustomerId(Long customerId) {
        Optional<Prerent> prerent = prerentRepository.findByCustomerId(customerId);
        return prerent.get();
    }

    @Override
    public void deleteByModelId(Long modelId) {
        prerentRepository.deleteByModelId(modelId);
    }
}
