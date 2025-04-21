package com.backend.inventaris.dto;

import com.backend.inventaris.enumm.Role;
import lombok.Data;

@Data
public class ResponseUserDTO {
    private String name;
    private String email;
    private String role;
}
