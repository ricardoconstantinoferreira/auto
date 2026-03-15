package com.ferreira.auto.repository;

import com.ferreira.auto.entity.Stock;
import com.ferreira.auto.entity.lib.StockInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    Stock findByModelId(Long modelId);

    @Query(nativeQuery = true, value =
    "select m.id as id, \n" +
    "m.description as description, \n" +
    "s.qtde as qtde \n"+
    "from model m \n" +
    "inner join stock s on s.model_id = m.id \n" +
    "where m.id = :modelId")
    StockInterface findStockWithModelByModelId(@Param("modelId") Long modelId);

}
