package com.backend.inventaris.repo;

import com.backend.inventaris.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepo extends JpaRepository<Product, Long> {
    Page<Product> findAllByIsDeleted(boolean isDeleted, Pageable pageable);
}
