package com.ferreira.auto.repository;

import com.ferreira.auto.entity.Expenses;
import com.ferreira.auto.entity.lib.ValueTotalInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpensesRepository extends JpaRepository<Expenses, Long> {

    @Query(value = "select SUM(value) as total_value from expenses " +
            "WHERE EXTRACT(MONTH FROM date_expenses) = CAST(:month AS INTEGER) " +
            "AND EXTRACT(YEAR FROM date_expenses) = CAST(:year AS INTEGER)",
            nativeQuery = true)
    ValueTotalInterface findValueTotalByPeriodExpenses(@Param("month") String month, @Param("year") String year);
}
