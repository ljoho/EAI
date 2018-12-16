package com.shopstantlyeshop.repository;

import com.shopstantlyeshop.model.Preferences;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreferencesRepository extends JpaRepository<Preferences, Long> {
    Preferences findByName(String name);
}
