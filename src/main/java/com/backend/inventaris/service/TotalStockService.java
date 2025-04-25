package com.backend.inventaris.service;

import com.backend.inventaris.core.IService;
import com.backend.inventaris.enumm.TypeTransaction;
import com.backend.inventaris.model.TotalStock;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public class TotalStockService implements IService<TotalStock> {
    @Override
    public ResponseEntity<Object> create(TotalStock totalStock, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> update(Long id, TotalStock totalStock, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> delete(Long id, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> findByParam(Pageable pageable, TypeTransaction typeTransaction, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> findById(Long id, HttpServletRequest request) {
        return null;
    }
}
