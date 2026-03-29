package com.ferreira.auto.repository;

import com.ferreira.auto.entity.Model;
import com.ferreira.auto.entity.lib.ModelInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ModelRepository extends JpaRepository<Model, Long> {
    Optional<Model> findByDescription(String description);

    Optional<Model> findByDescriptionIgnoreCase(String description);

    Optional<Model> findById(Long id);

    @Query(nativeQuery = true, value =
    "select \n" +
        "m.id, \n" +
        "m.description, \n" +
        "m.image, \n" +
        "m.year, \n" +
        "m.carmaker_id as carmaker_id, \n" +
        "m.category_id as category_id, \n" +
        "m.active, \n" +
        "m.price, \n" +
        "ca.description as descriptionCategory, \n" +
        "c.description as descriptionCarmaker, \n" +
        "s.qtde as qtde \n" +
        "from model m \n" +
        "inner join carmaker c on m.carmaker_id = c.id \n" +
        "inner join category ca on m.category_id = ca.id \n" +
        "inner join stock s on s.model_id = m.id \n" +
        "where s.qtde > 0"
    )
    List<ModelInterface> getAllModels();

    @Query(nativeQuery = true, value =
    "select \n" +
        "id, \n" +
        "description, \n" +
        "image, \n" +
        "year, \n" +
        "m.carmaker_id as carmaker_id, \n" +
        "m.category_id as category_id, \n" +
        "m.active, \n" +
        "m.price \n" +
        "from model m \n" +
        "where category_id = :categoryId")
    List<Model> getModelByCategoryId(Long categoryId);
}
