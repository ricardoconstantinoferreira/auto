package com.ferreira.auto.service.impl;

import com.ferreira.auto.dto.CategoryDto;
import com.ferreira.auto.entity.Category;
import com.ferreira.auto.infra.configuration.MessageInternationalization;
import com.ferreira.auto.repository.CategoryRepository;
import com.ferreira.auto.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    private MessageInternationalization messageInternationalization;

    @Override
    public Category save(CategoryDto categoryDto) {
        Category category = new Category();

        if (categoryDto.getId() != null) {
            category.setId(categoryDto.getId());
        }

        category.setDescription(categoryDto.getDescription());
        return categoryRepository.save(category);
    }

    @Override
    public Category getById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException(
                    messageInternationalization.getMessage("category.no.find")
                ));
    }

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public void delete(Long categoryId) {
        Category category = getById(categoryId);
        categoryRepository.delete(category);
    }

    @Override
    public boolean hasCategoryByDescription(String description) {
        Optional<Category> category = categoryRepository.findByDescription(description);
        return !category.isEmpty();
    }

}
