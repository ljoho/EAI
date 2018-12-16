package com.shopstantlypayment.repository;

import com.shopstantlypayment.model.Customers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface CustomersRepository extends JpaRepository<Customers, Long> {
    Customers findByCustomersID(@Param("CustomersID") Long id);
}
