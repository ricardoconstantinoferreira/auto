package com.ferreira.auto.repository;

import com.ferreira.auto.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    Stock findByModelId(Long modelId);
}
