package com.ferreira.auto.service;

import com.ferreira.auto.dto.StockDto;
import com.ferreira.auto.entity.StatusOrder;
import com.ferreira.auto.entity.Stock;
import com.ferreira.auto.entity.lib.StockInterface;

import java.util.List;

public interface StockService {

    Stock save(Stock stock);
    List<Stock> getAll();
    Stock getById(Long id);
    Stock getByModelId(Long modelId);
    StockInterface findStockWithModelByModelId(Long modelId);
    Stock update(Long id, StockDto stockDto);
    void delete(Long id);
    void updateQtdeByModelId(Long modelId, StatusOrder statusOrder);
}
