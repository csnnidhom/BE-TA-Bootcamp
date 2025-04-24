package com.backend.inventaris.core;

import com.backend.inventaris.enumm.TypeTransaction;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface IService<T> {
    ResponseEntity<Object> create(T t, HttpServletRequest request);//001-010
    ResponseEntity<Object> update(Long id,T t, HttpServletRequest request);//011-020
    ResponseEntity<Object> delete(Long id, HttpServletRequest request);//021-030
    ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request);//031-040
    public ResponseEntity<Object> findByParam(Pageable pageable, TypeTransaction typeTransaction, HttpServletRequest request);//041-050

}
