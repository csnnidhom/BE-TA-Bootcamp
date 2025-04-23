package com.backend.inventaris.dto.rel;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RelPeriodeDTO {
    @NotNull
    @NotBlank
    private Long id;

    @JsonProperty("name_periode")
    @NotNull
    @NotBlank
    private String namePeriode;

    public String getNamePeriode() {
        return namePeriode;
    }

    public void setNamePeriode(String namePeriode) {
        this.namePeriode = namePeriode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
