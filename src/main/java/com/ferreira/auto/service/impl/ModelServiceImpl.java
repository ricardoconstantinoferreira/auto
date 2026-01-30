package com.ferreira.auto.service.impl;

import com.ferreira.auto.dto.ModelDto;
import com.ferreira.auto.entity.Carmaker;
import com.ferreira.auto.entity.Model;
import com.ferreira.auto.repository.ModelRepository;
import com.ferreira.auto.service.CarmakerService;
import com.ferreira.auto.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ModelServiceImpl implements ModelService {

    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private CarmakerService carmakerService;

    @Override
    public Model save(ModelDto modelDto) {
        Model model = new Model();

        if (modelDto.getId() != null) {
            model.setId(modelDto.getId());
        }
        Carmaker carmaker = carmakerService.getById(modelDto.getCarmakerId());
        model.setCarmaker(carmaker);
        model.setDescription(modelDto.getDescription());
        model.setYear(modelDto.getYear());
        model.setImage(modelDto.getImage());
        model.setActive(true);

        return modelRepository.save(model);
    }

    @Override
    public Model getById(Long id) {
        return modelRepository.getReferenceById(id);
    }

    @Override
    public List<Model> getAll() {
        return modelRepository.findAll();
    }

    @Override
    public boolean hasModel(String description) {
        Optional<Model> model = modelRepository.findByDescription(description);
        return !model.isEmpty();
    }

    @Override
    public void inactivate(Long id) {
        Model model = this.getById(id);
        model.setActive(false);
        modelRepository.save(model);
    }

    @Override
    public void activate(Long id) {
        Model model = this.getById(id);
        model.setActive(true);
        modelRepository.save(model);
    }
}
