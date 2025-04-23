package com.backend.inventaris.repo;

import com.backend.inventaris.enumm.TypeTransaction;
import com.backend.inventaris.model.Product;
import com.backend.inventaris.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionRepo extends JpaRepository<Transaction, Long> {

}
