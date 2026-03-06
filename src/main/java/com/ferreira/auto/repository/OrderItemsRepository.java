package com.ferreira.auto.repository;

import com.ferreira.auto.entity.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemsRepository extends JpaRepository<OrderItems, Long> {

    @Query("SELECT o FROM OrderItems o JOIN FETCH o.order where o.customer.id = :customerId")
    List<OrderItems> findByOrderByCustomerId(@Param("customerId") Long customerId);

    @Query("SELECT o FROM OrderItems o JOIN FETCH o.order where o.order.id = :orderId")
    List<OrderItems> findByOrderByOrderId(@Param("orderId") Long orderId);
}
