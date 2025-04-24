package com.backend.inventaris.dto.validation;

import com.backend.inventaris.util.ConstantsMessage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class ValDataMasterDTO {

    @Pattern(regexp = "^[\\w\\s]{3,50}$",message = ConstantsMessage.VAL_NAME)
    @NotNull
    @NotBlank
    private String name;

    private Boolean active;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
