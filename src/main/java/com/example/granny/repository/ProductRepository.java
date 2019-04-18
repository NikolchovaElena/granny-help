package com.example.granny.repository;

import com.example.granny.domain.entities.Product;
import com.example.granny.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Optional<Product> findByName(String name);

    @Query(value = "SELECT * " +
            "FROM products p " +
            "ORDER BY RAND() " +
            "LIMIT 5", nativeQuery = true)
    List<Product> findFiveRandomProducts();
}
