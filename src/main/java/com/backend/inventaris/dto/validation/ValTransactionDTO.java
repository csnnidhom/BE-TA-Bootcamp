package com.backend.inventaris.dto.validation;

import com.backend.inventaris.dto.rel.RelPeriodeDTO;
import com.backend.inventaris.dto.rel.RelProductDTO;
import com.backend.inventaris.dto.rel.RelWarehouseDTO;
import com.backend.inventaris.enumm.TypeTransaction;
import com.backend.inventaris.util.ConstantsMessage;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class ValTransactionDTO {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    private Long price;

    private Long qty;

    @JsonProperty("type_transaction")
    private TypeTransaction typeTransaction;

    @NotNull(message = ConstantsMessage.VAL_RELASI)
    @JsonProperty("product")
    private RelProductDTO relProductDTO;

    @NotNull(message = ConstantsMessage.VAL_RELASI)
    @JsonProperty("warehouse")
    private RelWarehouseDTO relWarehouseDTO;

//    @NotNull(message = ConstantsMessage.VAL_RELASI)
//    @JsonProperty("periode")
//    private RelPeriodeDTO relPeriodeDTO;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    public TypeTransaction getTypeTransaction() {
        return typeTransaction;
    }

    public void setTypeTransaction(TypeTransaction typeTransaction) {
        this.typeTransaction = typeTransaction;
    }

    public RelProductDTO getRelProductDTO() {
        return relProductDTO;
    }

    public void setRelProductDTO(RelProductDTO relProductDTO) {
        this.relProductDTO = relProductDTO;
    }

    public RelWarehouseDTO getRelWarehouseDTO() {
        return relWarehouseDTO;
    }

    public void setRelWarehouseDTO(RelWarehouseDTO relWarehouseDTO) {
        this.relWarehouseDTO = relWarehouseDTO;
    }

//    public RelPeriodeDTO getRelPeriodeDTO() {
//        return relPeriodeDTO;
//    }
//
//    public void setRelPeriodeDTO(RelPeriodeDTO relPeriodeDTO) {
//        this.relPeriodeDTO = relPeriodeDTO;
//    }
}
