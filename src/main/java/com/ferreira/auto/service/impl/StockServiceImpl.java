package com.ferreira.auto.service.impl;

import com.ferreira.auto.dto.StockDto;
import com.ferreira.auto.entity.Model;
import com.ferreira.auto.entity.Stock;
import com.ferreira.auto.repository.ModelRepository;
import com.ferreira.auto.repository.StockRepository;
import com.ferreira.auto.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private ModelRepository modelRepository;

    @Override
    public Stock save(StockDto stockDto) {
        if (stockDto.getModelId() == null) {
            throw new IllegalArgumentException("modelId is required");
        }
        Optional<Model> modelOpt = modelRepository.findById(stockDto.getModelId());
        if (modelOpt.isEmpty()) {
            throw new IllegalArgumentException("Model not found");
        }

        Stock stock = new Stock();
        stock.setQtde(stockDto.getQtde());
        stock.setModel(modelOpt.get());
        return stockRepository.save(stock);
    }

    @Override
    public List<Stock> getAll() {
        return stockRepository.findAll();
    }

    @Override
    public Stock getById(Long id) {
        return stockRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Stock not found"));
    }

    @Override
    public List<Stock> getByModelId(Long modelId) {
        return stockRepository.findByModelId(modelId);
    }

    @Override
    public Stock update(Long id, StockDto stockDto) {
        Stock s = stockRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Stock not found"));
        s.setQtde(stockDto.getQtde());
        if (stockDto.getModelId() != null) {
            Model m = modelRepository.findById(stockDto.getModelId())
                    .orElseThrow(() -> new IllegalArgumentException("Model not found"));
            s.setModel(m);
        }
        return stockRepository.save(s);
    }

    @Override
    public void delete(Long id) {
        Stock s = stockRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Stock not found"));
        stockRepository.delete(s);
    }
}
