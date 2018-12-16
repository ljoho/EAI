package com.shopstantlyeshop.repository;

import com.shopstantlyeshop.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsRepository extends JpaRepository<Products, Long> {
    Products findByName(String name);
}
