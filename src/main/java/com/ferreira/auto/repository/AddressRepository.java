package com.ferreira.auto.repository;

import com.ferreira.auto.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    @Query(nativeQuery = true, value =
            "SELECT \n" +
            "    a.id,\n" +
            "    a.address, \n" +
            "    a.city, \n" +
            "    a.complement,\n" +
            "    a.neighborhood,\n" +
            "    a.number,\n" +
            "    a.state,\n" +
            "    a.zipcode, \n" +
            "    a.customer_id \n" +
            "FROM address a \n" +
            "INNER JOIN customer c ON c.id = a.customer_id \n" +
            "WHERE a.customer_id = :customerId \n" +
            "ORDER BY a.id DESC \n" +
            "LIMIT 1"
    )
    Address getAddressByIdAndCustomerId(@Param("customerId") Long customerId);
}
