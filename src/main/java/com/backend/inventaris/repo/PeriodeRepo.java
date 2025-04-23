package com.backend.inventaris.repo;

import com.backend.inventaris.model.Periode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PeriodeRepo extends JpaRepository<Periode, Long> {
    Page<Periode> findAllByIsDeleted(boolean isDeleted, Pageable pageable);

    Optional<Periode> findFirstByActive(Boolean activePeriode);
}
