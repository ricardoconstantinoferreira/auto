package com.ferreira.auto.repository;

import com.ferreira.auto.entity.Order;
import com.ferreira.auto.entity.lib.OrderInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findById(Long id);

    @Query("SELECT o FROM Order o WHERE o.customer.id = :customerId")
    List<Order> findByCustomerId(Long customerId);

    @Query("select c.name as customer, o.dateOrder as dateOrder, o.totalPrice as totalPrice, " +
            "o.statusOrder as statusOrder, o.interestValuePayment as interestValuePayment " +
            "from Order o join o.customer c")
    List<OrderInterface> findByListOrderRent();

}
