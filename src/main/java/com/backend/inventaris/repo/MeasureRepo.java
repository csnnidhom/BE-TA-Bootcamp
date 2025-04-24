package com.backend.inventaris.repo;

import com.backend.inventaris.model.Measure;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MeasureRepo extends JpaRepository<Measure, Long> {
    Page<Measure> findAllByIsDeleted(boolean b, Pageable pageable);

}
