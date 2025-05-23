package com.backend.inventaris.controller;

import com.backend.inventaris.config.OtherConfig;
import com.backend.inventaris.dto.validation.ValDataMasterDTO;
import com.backend.inventaris.service.WarehouseService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/warehouse")
public class WarehouseController {

    @Autowired
    private WarehouseService warehouseService;

    @PostMapping("/create")
    public ResponseEntity<Object> create(@Valid @RequestBody ValDataMasterDTO valDataMasterDTO, HttpServletRequest request) {
        return warehouseService.create(warehouseService.converToEntity(valDataMasterDTO), request);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllWarehouses(HttpServletRequest request) {
        Pageable pageable = PageRequest.of(0, OtherConfig.getPageDefault(), Sort.by("id"));
        return warehouseService.findAll(pageable,request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@Valid @PathVariable Long id,HttpServletRequest request) {
        return warehouseService.findById(id,request);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id,
                                         @Valid @RequestBody ValDataMasterDTO valDataMasterDTO,
                                         HttpServletRequest request) {
        return warehouseService.update(id, warehouseService.converToEntity(valDataMasterDTO), request);
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id,
                                         HttpServletRequest request) {
        return warehouseService.delete(id, request);
    }

}
