package com.demo.BackendApi.repository;

import com.demo.BackendApi.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency, String> {
}
