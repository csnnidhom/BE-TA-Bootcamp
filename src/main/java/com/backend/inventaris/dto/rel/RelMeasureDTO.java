package com.backend.inventaris.dto.rel;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RelMeasureDTO {
    @NotNull
    @NotBlank
    private Long id;

    @JsonProperty("name_measure")
    @NotNull
    @NotBlank
    private String nameMeasure;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameMeasure() {
        return nameMeasure;
    }

    public void setNameMeasure(String nameMeasure) {
        this.nameMeasure = nameMeasure;
    }
}
