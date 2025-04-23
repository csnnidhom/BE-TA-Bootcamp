package com.backend.inventaris.controller;

import com.backend.inventaris.config.OtherConfig;
import com.backend.inventaris.dto.validation.ValProductDTO;
import com.backend.inventaris.model.Product;
import com.backend.inventaris.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<Object> create(@Valid @RequestBody ValProductDTO valProductDTO,
                                         HttpServletRequest request) {
        return productService.create(productService.converToEntity(valProductDTO), request);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllWarehouses(HttpServletRequest request) {
        Pageable pageable = PageRequest.of(0, OtherConfig.getPageDefault(), Sort.by("id"));
        return productService.findAll(pageable,request);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> update(@Valid @PathVariable Long id,
                                         @RequestBody ValProductDTO valProductDTO,
                                         HttpServletRequest request){
        return productService.update(id,productService.converToEntity(valProductDTO), request);
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id,
                                         HttpServletRequest request){
        return productService.delete(id,request);
    }
}
