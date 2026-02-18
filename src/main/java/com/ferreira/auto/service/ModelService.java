package com.ferreira.auto.service;

import com.ferreira.auto.dto.ModelRecord;
import com.ferreira.auto.entity.Model;

import java.util.List;

public interface ModelService {

    Model save(ModelRecord modelRecord);
    Model getById(Long id);
    List<Model> getAll();
    void inactivate(Long id);
    void activate(Long id);
    boolean hasModel(String description);
}
