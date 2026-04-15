package com.ferreira.auto.repository;

import com.ferreira.auto.entity.ExpensesType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpensesTypeRepository extends JpaRepository<ExpensesType, Long> {
}
