package com.backend.inventaris.dto.validation;

import com.backend.inventaris.util.ConstantsMessage;
import jakarta.validation.constraints.Pattern;

public class ValDataMasterDTO {

    @Pattern(regexp = "^[\\w\\s]{5,100}$",message = ConstantsMessage.VAL_NAME)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
