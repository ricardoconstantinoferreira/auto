package com.ferreira.auto.repository;

import com.ferreira.auto.entity.Prerent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrerentRepository extends JpaRepository<Prerent, Long> {

    Optional<Prerent> findByCustomerId(Long id);

    void deleteByModelId(Long id);
}
