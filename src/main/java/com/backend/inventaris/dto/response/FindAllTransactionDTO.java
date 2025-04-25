package com.backend.inventaris.dto.response;

import com.backend.inventaris.dto.rel.FindTotalStockDTO;
import com.backend.inventaris.enumm.TypeTransaction;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class FindAllTransactionDTO {
    private Long id;
    private FindProductDTO product;
    private Long qty;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    @JsonProperty("type_transaction")
    private TypeTransaction typeTransaction;
    private FindWarehouseDTO warehouse;
    private FindPeriodeDTO periode;
    @JsonProperty("last_stock")
    private FindTotalStockDTO totalStock;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FindProductDTO getProduct() {
        return product;
    }

    public void setProduct(FindProductDTO product) {
        this.product = product;
    }

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    public FindWarehouseDTO getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(FindWarehouseDTO warehouse) {
        this.warehouse = warehouse;
    }

    public FindPeriodeDTO getPeriode() {
        return periode;
    }

    public void setPeriode(FindPeriodeDTO periode) {
        this.periode = periode;
    }

    public TypeTransaction getTypeTransaction() {
        return typeTransaction;
    }

    public void setTypeTransaction(TypeTransaction typeTransaction) {
        this.typeTransaction = typeTransaction;
    }

    public FindTotalStockDTO getTotalStock() {
        return totalStock;
    }

    public void setTotalStock(FindTotalStockDTO totalStock) {
        this.totalStock = totalStock;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
