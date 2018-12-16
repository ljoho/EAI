package com.shopstantlyeshop.repository;

import com.shopstantlyeshop.model.CreditCards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardsRepository extends JpaRepository<CreditCards, Long> {
}
