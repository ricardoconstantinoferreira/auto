package com.ferreira.auto.repository;

import com.ferreira.auto.entity.Carmaker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarmakerRepository extends JpaRepository<Carmaker, Long> {

    Optional<Carmaker> findByDescription(String description);
}
