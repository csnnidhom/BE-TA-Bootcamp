package com.backend.inventaris.dto.validation;

import com.backend.inventaris.dto.rel.RelMeasureDTO;
import com.backend.inventaris.util.ConstantsMessage;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class ValProductDTO {
    @Pattern(regexp = "^[\\w\\s]{5,100}$",message = ConstantsMessage.VAL_NAME)
    private String name;

    @NotNull(message = ConstantsMessage.VAL_RELASI)
    @JsonProperty("measure")
    private RelMeasureDTO relMeasureDTO;

    @NotNull
    private Long price;

    @NotNull
    @JsonProperty("warn_stock")
    private Long warnStock;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RelMeasureDTO getRelMeasureDTO() {
        return relMeasureDTO;
    }

    public void setRelMeasureDTO(RelMeasureDTO relMeasureDTO) {
        this.relMeasureDTO = relMeasureDTO;
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
}
