package com.ferreira.auto.service;

import com.ferreira.auto.dto.ModelRecord;
import com.ferreira.auto.entity.Model;
import com.ferreira.auto.entity.lib.ModelInterface;

import java.util.List;

public interface ModelService {

    Model save(ModelRecord modelRecord);
    Model getById(Long id);
    List<ModelInterface> getAll();
    void inactivate(Long id);
    void activate(Long id);
    boolean hasModel(String description);
    List<Model> getModelByCategoryId(Long categoryId);
}
