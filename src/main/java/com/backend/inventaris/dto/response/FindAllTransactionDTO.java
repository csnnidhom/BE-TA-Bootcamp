package com.backend.inventaris.dto.response;

public class FindAllTransactionDTO {
    private Long id;
    private FindProductDTO product;
    private Long qty;
    private FindWarehouseDTO warehouse;
    private FindPeriodeDTO periode;

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
}
