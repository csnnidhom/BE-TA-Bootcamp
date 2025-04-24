package com.backend.inventaris.dto.response;

import lombok.Data;

@Data
public class FindUserDTO {
    private String name;
    private String email;
    private String role;
}
