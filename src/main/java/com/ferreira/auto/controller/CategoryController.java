package com.ferreira.auto.controller;

import com.ferreira.auto.dto.CategoryDto;
import com.ferreira.auto.dto.CategoryResponseDto;
import com.ferreira.auto.entity.Category;
import com.ferreira.auto.entity.Model;
import com.ferreira.auto.exception.CategoryAlreadyExistsException;
import com.ferreira.auto.exception.NoRemoveCategoryException;
import com.ferreira.auto.infra.configuration.MessageInternationalization;
import com.ferreira.auto.service.CategoryService;
import com.ferreira.auto.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "api/auto/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ModelService modelService;

    @Autowired
    private MessageInternationalization messageInternationalization;

    @PostMapping
    public ResponseEntity<CategoryResponseDto> save(@RequestBody CategoryDto categoryDto) {
        if (categoryService.hasCategoryByDescription(categoryDto.getDescription())) {
            throw new CategoryAlreadyExistsException(
                    messageInternationalization.getMessage("category.exists.message"),
                    categoryDto.getDescription()
            );
        }

        Category entity = categoryService.save(categoryDto);
        Optional<Category> optionalCategory = Optional.ofNullable(entity);
        CategoryResponseDto categoryResponseDto = new CategoryResponseDto(
                messageInternationalization.getMessage("category.save.message"), "200",
                optionalCategory.orElse(null)
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(categoryResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> update(@PathVariable(value = "id") Long id,
                                                      @RequestBody CategoryDto categoryDto) {
        categoryDto.setId(id);

        Category entity = categoryService.save(categoryDto);
        Optional<Category> optionalCategory = Optional.ofNullable(entity);
        CategoryResponseDto categoryResponseDto = new CategoryResponseDto(
                messageInternationalization.getMessage("category.updated.message"), "200", optionalCategory.orElse(null)
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(categoryResponseDto);

    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable(value = "id") Long id) {
        Category category = categoryService.getById(id);
        List<Model> model = modelService.getModelByCategoryId(id);

        if (!model.isEmpty()) {
            throw new NoRemoveCategoryException(
                    messageInternationalization.getMessage("category.noallow.remove"),
                    category.getDescription()
            );
        }

        categoryService.delete(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getById(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(categoryService.getById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAll() {
        return new ResponseEntity<>(categoryService.getAll(), HttpStatus.OK);
    }
}
