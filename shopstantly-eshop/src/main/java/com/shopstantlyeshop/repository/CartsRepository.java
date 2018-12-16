package com.shopstantlyeshop.repository;

import com.shopstantlyeshop.model.Carts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartsRepository extends JpaRepository<Carts, Long> {
}
