package com.ferreira.auto.repository;

import com.ferreira.auto.entity.Order;
import com.ferreira.auto.entity.StatusOrder;
import com.ferreira.auto.entity.lib.CustomerGraphicInterface;
import com.ferreira.auto.entity.lib.ModelGraphicInterface;
import com.ferreira.auto.entity.lib.OrderInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findById(Long id);

    @Query("SELECT o FROM Order o WHERE o.customer.id = :customerId")
    List<Order> findByCustomerId(Long customerId);

    @Query("select o.id as id, c.name as customer, o.dateOrder as dateOrder, o.totalPrice as totalPrice, " +
            "o.statusOrder as statusOrder, o.interestValuePayment as interestValuePayment " +
            "from Order o join o.customer c where o.statusOrder = :status")
    List<OrderInterface> findByListOrderRent(@Param("status") StatusOrder status);

    @Query(value = "SELECT m.description as description, COUNT(oi.model_id) as qtde " +
            "FROM model m " +
            "INNER JOIN order_items oi ON oi.model_id = m.id " +
            "INNER JOIN orders o ON o.id = oi.order_id " +
            "WHERE EXTRACT(MONTH FROM o.date_order) = CAST(:month AS INTEGER) " +
            "AND EXTRACT(YEAR FROM o.date_order) = CAST(:year AS INTEGER) " +
            "GROUP BY m.description",
    nativeQuery = true)
    List<ModelGraphicInterface> findQtdeModelByPeriod(@Param("month") String month, @Param("year") String year);

    @Query(value = "select SUM(total_price + COALESCE(interest_value_payment, 0)) as total_value from orders " +
            "WHERE EXTRACT(MONTH FROM date_order) = CAST(:month AS INTEGER) " +
            "AND EXTRACT(YEAR FROM date_order) = CAST(:year AS INTEGER) and status_order = :#{#status.ordinal()}",
    nativeQuery = true)
    BigDecimal findValueTotalByPeriod(@Param("month") String month, @Param("year") String year);

    @Query(value = "SELECT c.name, count(oi.customer_id) as qtde " +
            "FROM customer c " +
            "INNER JOIN order_items oi on oi.customer_id = c.id " +
            "INNER JOIN orders o ON o.id = oi.order_id " +
            "WHERE EXTRACT(MONTH FROM o.date_order) = CAST(:month AS INTEGER) " +
            "AND EXTRACT(YEAR FROM o.date_order) = CAST(:year AS INTEGER) " +
            "GROUP BY c.name",
            nativeQuery = true)
    List<CustomerGraphicInterface> findQtdeCustomerByPeriod(@Param("month") String month, @Param("year") String year);
}
