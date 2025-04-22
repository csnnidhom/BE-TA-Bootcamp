package com.backend.inventaris.controller;

import com.backend.inventaris.config.OtherConfig;
import com.backend.inventaris.dto.validation.ValPeriodeDTO;
import com.backend.inventaris.service.PeriodeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/periode")
public class PeriodeController {
    
    @Autowired
    private PeriodeService periodeService;

    @PostMapping("/create")
    public ResponseEntity<Object> create(@Valid @RequestBody ValPeriodeDTO valPeriodeDTO, HttpServletRequest request) {
        return periodeService.create(periodeService.converToEntity(valPeriodeDTO), request);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllWarehouses(HttpServletRequest request) {
        Pageable pageable = PageRequest.of(0, OtherConfig.getPageDefault(), Sort.by("id"));
        return periodeService.findAll(pageable,request);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id,
                                         @Valid @RequestBody ValPeriodeDTO valPeriodeDTO,
                                         HttpServletRequest request) {
        return periodeService.update(id, periodeService.converToEntity(valPeriodeDTO), request);
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id,
                                         @Valid @RequestBody ValPeriodeDTO valPeriodeDTO,
                                         HttpServletRequest request) {
        return periodeService.delete(id, request);
    }
}
