package com.ferreira.auto.repository;

import com.ferreira.auto.entity.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ModelRepository extends JpaRepository<Model, Long> {
    Optional<Model> findByDescription(String description);

    @Query(nativeQuery = true, value =
    "select \n" +
        "m.id, \n" +
        "m.description, \n" +
        "m.image, \n" +
        "m.year, \n" +
        "m.carmaker_id as carmaker_id, \n" +
        "m.active, \n" +
        "m.price, \n" +
        "c.description as descriptionCarmaker \n" +
        "from model m\n" +
        "inner join carmaker c on m.carmaker_id = c.id")
    List<Model> getAllModels();
}
