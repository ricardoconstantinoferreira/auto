package com.ferreira.auto.repository;

import com.ferreira.auto.entity.Prerent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrerentRepository extends JpaRepository<Prerent, Long> {

    @Query(nativeQuery = true, value =
            "select p.* from prerent p \n" +
                    "inner join customer c on c.id = p.customer_id \n" +
                    "inner join model m on m.id = p.model_id \n" +
                    "where p.customer_id = :customerId and p.status = 0")
    List<Prerent> findByCustomerId(Long customerId);

    @Query(nativeQuery = true, value =
            "select * from prerent \n" +
            "where customer_id = :customerId and model_id = :modelId and status = 0")
    Prerent findByCustomerIdAndModelId(Long customerId, Long modelId);

    @Query(nativeQuery = true, value =
            "select COUNT(*) as qty from prerent \n" +
                    "where customer_id = :customerId and status = 0")
    Long findQtyPrerentByCustomer(Long customerId);

    void deleteByModelId(Long id);
}
