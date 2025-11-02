package com.gestorventas.deposito.dto.in;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String email;
    private String password;
}
