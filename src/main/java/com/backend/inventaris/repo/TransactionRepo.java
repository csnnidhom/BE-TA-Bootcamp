package com.backend.inventaris.repo;

import com.backend.inventaris.enumm.TypeTransaction;
import com.backend.inventaris.model.Periode;
import com.backend.inventaris.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionRepo extends JpaRepository<Transaction, Long> {

    Page<Transaction> findAllByTypeTransaction(Pageable pageable, TypeTransaction typeTransaction);
}
