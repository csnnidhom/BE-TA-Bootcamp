package com.backend.inventaris.dto.validation;

import jakarta.validation.constraints.Pattern;

public class ValWarehouseDTO {

    @Pattern(regexp = "^[\\w\\s]{5,100}$",message = "Alfanumerik dengan spasi min 5 maks 100 karakter")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
