package com.backend.inventaris.controller;

import com.backend.inventaris.dto.validation.ValTransactionDTO;
import com.backend.inventaris.service.TransactionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/create/transaction-sell-or-buy")
    public ResponseEntity<Object> createTransactionSellOrBuy(@Valid @RequestBody ValTransactionDTO valTransactionDTO,
                                         HttpServletRequest request) {
        return transactionService.create(transactionService.converToEntity(valTransactionDTO), request);
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id,
                                         HttpServletRequest request){
        return transactionService.delete(id,request);
    }


}
