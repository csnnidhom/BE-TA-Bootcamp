package com.backend.inventaris.repo;

import com.backend.inventaris.model.Warehouse;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;

public interface WarehouseRepo extends JpaRepository<Warehouse, Long> {
    Page<Warehouse> findAllByIsDeleted(Boolean isDeleted, Pageable pageable);
}
