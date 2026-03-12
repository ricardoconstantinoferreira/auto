package com.ferreira.auto.service;

import com.ferreira.auto.dto.CategoryDto;
import com.ferreira.auto.entity.Category;

import java.util.List;

public interface CategoryService {

    Category save(CategoryDto categoryDto);
    Category getById(Long categoryId);
    List<Category> getAll();
    void delete(Long categoryId);
    boolean hasCategoryByDescription(String description);
}
