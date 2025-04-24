package com.backend.inventaris.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
public class FindMeasureDTO {
    private Long id;

    @JsonProperty("name_measure")
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
