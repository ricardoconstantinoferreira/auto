package com.ferreira.auto.service.impl;

import com.ferreira.auto.dto.StockDto;
import com.ferreira.auto.entity.Model;
import com.ferreira.auto.entity.StatusOrder;
import com.ferreira.auto.entity.Stock;
import com.ferreira.auto.entity.lib.StockInterface;
import com.ferreira.auto.repository.ModelRepository;
import com.ferreira.auto.repository.StockRepository;
import com.ferreira.auto.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private ModelRepository modelRepository;

    @Override
    public Stock save(Stock stock) {
        if (stock.getModel().getId() == null) {
            throw new IllegalArgumentException("modelId is required");
        }
        Optional<Model> modelOpt = modelRepository.findById(stock.getModel().getId());
        if (modelOpt.isEmpty()) {
            throw new IllegalArgumentException("Model not found");
        }

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
    public Stock getByModelId(Long modelId) {
        return stockRepository.findByModelId(modelId);
    }

    @Override
    public StockInterface findStockWithModelByModelId(Long modelId) {
        return stockRepository.findStockWithModelByModelId(modelId);
    }

    @Override
    public Stock update(Long id, StockDto stockDto) {
        Stock s = stockRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Stock not found"));
        if (stockDto.getQtde() == null) {
            throw new IllegalArgumentException("qtde is required");
        }
        if (stockDto.getQtde() < 0) {
            throw new IllegalArgumentException("qtde must be non-negative");
        }
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

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void updateQtdeByModelId(Long modelId, StatusOrder statusOrder) {
        Stock stock = getByModelId(modelId);

        if (statusOrder == StatusOrder.RENTED) {
            stock.setQtde(stock.getQtde() - 1);
        } else {
            stock.setQtde(stock.getQtde() + 1);
        }

        stockRepository.save(stock);
    }
}
