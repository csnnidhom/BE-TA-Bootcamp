package com.backend.inventaris.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FindProductDTO {
    private Long id;
    private String name;
    @JsonProperty("measure")
    private FindMeasureDTO measure;
    private Long price;
    @JsonProperty("warn_stock")
    private Long warnStock;
    private Boolean isDeleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FindMeasureDTO getMeasure() {
        return measure;
    }

    public void setMeasure(FindMeasureDTO measure) {
        this.measure = measure;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getWarnStock() {
        return warnStock;
    }

    public void setWarnStock(Long warnStock) {
        this.warnStock = warnStock;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

}
