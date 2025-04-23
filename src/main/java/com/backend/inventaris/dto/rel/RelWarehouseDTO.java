package com.backend.inventaris.dto.rel;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RelWarehouseDTO {
    @NotNull
    @NotBlank
    private Long id;

    @JsonProperty("name_warehouse")
    @NotNull
    @NotBlank
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
