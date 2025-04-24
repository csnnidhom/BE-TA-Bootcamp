package com.backend.inventaris.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FindWarehouseDTO {
    private Long id;

    @JsonProperty("name_warehouse")
    private String nameWarehouse;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameWarehouse() {
        return nameWarehouse;
    }

    public void setNameWarehouse(String nameWarehouse) {
        this.nameWarehouse = nameWarehouse;
    }
}
