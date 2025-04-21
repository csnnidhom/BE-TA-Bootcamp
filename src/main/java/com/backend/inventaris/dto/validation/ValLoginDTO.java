package com.backend.inventaris.dto.validation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
public class ValLoginDTO implements Serializable {

    private final static long serialVersionUID = 1L;

    @NotEmpty
    @NotNull
    @NotBlank
    private String email;

    @NotEmpty
    @NotNull
    @NotBlank
    private String password;

}