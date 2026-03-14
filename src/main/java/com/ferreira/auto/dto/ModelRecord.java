package com.ferreira.auto.dto;

import org.springframework.web.multipart.MultipartFile;

public record ModelRecord(
        Long id,
        String description,
        Long carmakerId,
        Long categoryId,
        int year,
        boolean active,
        MultipartFile image,
        float price,
        Integer qtde
) { }
