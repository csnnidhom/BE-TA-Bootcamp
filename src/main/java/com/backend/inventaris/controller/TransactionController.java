package com.backend.inventaris.controller;

import com.backend.inventaris.config.OtherConfig;
import com.backend.inventaris.dto.validation.ValProductDTO;
import com.backend.inventaris.dto.validation.ValTransactionDTO;
import com.backend.inventaris.enumm.TypeTransaction;
import com.backend.inventaris.service.TransactionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/create")
    public ResponseEntity<Object> createTransactionSellOrBuy(@Valid @RequestBody ValTransactionDTO valTransactionDTO,
                                         HttpServletRequest request) {
        return transactionService.create(transactionService.converToEntity(valTransactionDTO), request);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> update(@Valid @PathVariable Long id,
                                         @RequestBody ValTransactionDTO valTransactionDTO,
                                         HttpServletRequest request){
        return transactionService.update(id,transactionService.converToEntity(valTransactionDTO), request);
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id,
                                         HttpServletRequest request){
        return transactionService.delete(id,request);
    }

    @GetMapping("/all/{type}")
    public ResponseEntity<Object> getAll(@Valid @PathVariable (value = "type") TypeTransaction typeTransaction,
                                         HttpServletRequest request) {
        Pageable pageable = PageRequest.of(0, OtherConfig.getPageDefault(), Sort.by("id"));
        return transactionService.findByParam(pageable,typeTransaction,request);
    }

}
