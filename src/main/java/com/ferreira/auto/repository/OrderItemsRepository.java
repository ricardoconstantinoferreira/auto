package com.ferreira.auto.repository;

import com.ferreira.auto.entity.OrderItems;
import com.ferreira.auto.entity.lib.OrderItemsInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemsRepository extends JpaRepository<OrderItems, Long> {

    @Query(nativeQuery = true, value =
            "SELECT m.description as model, m.image as image, m.year as year, \n " +
                    "oi.price as price, c.description as carmaker, ca.description as category from order_items oi \n" +
            "inner join model m on m.id = oi.model_id \n" +
            "inner join carmaker c on c.id = m.carmaker_id \n" +
            "inner join category ca on ca.id = m.category_id \n" +
            "where oi.order_id = :orderId and oi.customer_id = :customerId "
    )
    List<OrderItemsInterface> findByOrderItemsByOrderIdAndCustomerId(@Param("orderId") Long orderId,
                                                                     @Param("customerId") Long customerId);

    List<OrderItems> findByOrderId(Long orderId);
}
