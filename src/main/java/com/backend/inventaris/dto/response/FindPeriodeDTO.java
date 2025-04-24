package com.backend.inventaris.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FindPeriodeDTO {
    private Long id;

    @JsonProperty("name_periode")
    private String namePeriode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNamePeriode() {
        return namePeriode;
    }

    public void setNamePeriode(String namePeriode) {
        this.namePeriode = namePeriode;
    }
}
