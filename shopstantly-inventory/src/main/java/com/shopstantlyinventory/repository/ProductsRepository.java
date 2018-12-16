package com.shopstantlyinventory.repository;

import com.shopstantlyinventory.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface ProductsRepository extends JpaRepository<Products, Long> {
    Products findByProductsID(@Param("productsID") Long id);
}
