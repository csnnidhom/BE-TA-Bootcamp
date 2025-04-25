package com.backend.inventaris.repo;

import com.backend.inventaris.model.TotalStock;
import com.backend.inventaris.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TotalStockRepo extends JpaRepository<TotalStock, Long> {
}
