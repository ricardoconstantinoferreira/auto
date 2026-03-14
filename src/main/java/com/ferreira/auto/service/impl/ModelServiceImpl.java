package com.ferreira.auto.service.impl;

import com.ferreira.auto.dto.ModelRecord;
import com.ferreira.auto.entity.Carmaker;
import com.ferreira.auto.entity.Category;
import com.ferreira.auto.entity.Model;
import com.ferreira.auto.entity.Stock;
import com.ferreira.auto.infra.upload.Config;
import com.ferreira.auto.repository.ModelRepository;
import com.ferreira.auto.service.CarmakerService;
import com.ferreira.auto.service.CategoryService;
import com.ferreira.auto.service.ModelService;
import com.ferreira.auto.service.StockService;
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

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private StockService stockService;

    @Override
    public Model save(ModelRecord modelRecord) {
        Model model = new Model();

        if (modelRecord.id() != null) {
            model.setId(modelRecord.id());
            model.setImage(managmentImage(modelRecord));
        } else {
            model.setImage(Config.UPLOADS + "/" + modelRecord.image().getOriginalFilename());
        }

        Carmaker carmaker = carmakerService.getById(modelRecord.carmakerId());
        Category category = categoryService.getById(modelRecord.categoryId());
        model.setCarmaker(carmaker);
        model.setDescription(modelRecord.description());
        model.setYear(modelRecord.year());
        model.setPrice(modelRecord.price());
        model.setActive(true);
        model.setCategory(category);

        Model modelEntity = modelRepository.save(model);

        if (modelEntity != null) {
            Stock stock = new Stock();
            stock.setModel(model);
            stock.setQtde(modelRecord.qtde());

            stockService.save(stock);
        }

        return modelEntity;
    }

    @Override
    public Model getById(Long id) {
        Optional<Model> models = modelRepository.findById(id);
        return models.get();
    }

    @Override
    public List<Model> getAll() {
        return modelRepository.getAllModels();
    }

    @Override
    public boolean hasModel(String description) {
        Optional<Model> model = modelRepository.findByDescription(description);
        return !model.isEmpty();
    }

    @Override
    public List<Model> getModelByCategoryId(Long categoryId) {
        return modelRepository.getModelByCategoryId(categoryId);
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

    private String managmentImage(ModelRecord modelRecord) {
        if (modelRecord.image() != null) {
            return Config.UPLOADS + "/" + modelRecord.image().getOriginalFilename();
        }

        Model model = getById(modelRecord.id());
        return model.getImage();
    }
}
