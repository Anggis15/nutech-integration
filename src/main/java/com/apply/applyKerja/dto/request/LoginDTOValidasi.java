package com.apply.applyKerja.dto.request;

/*
IntelliJ IDEA 2022.3.1 (Community Edition)
Build #IC-223.8214.52, built on December 20, 2022
@Author asd a.k.a. Anggi Saputra
Java Developer
Created on 03/12/24 20.32
@Last Modified 03/12/24 20.32
Version 1.0
*/

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class LoginDTOValidasi {
    @NotNull
    @NotBlank
    @NotEmpty
    private String email;
    @NotNull
    @NotBlank
    @NotEmpty
    private String password;

    public @NotNull @NotBlank @NotEmpty String getEmail() {
        return email;
    }

    public void setEmail(@NotNull @NotBlank @NotEmpty String email) {
        this.email = email;
    }

    public @NotNull @NotBlank @NotEmpty String getPassword() {
        return password;
    }

    public void setPassword(@NotNull @NotBlank @NotEmpty String password) {
        this.password = password;
    }
}
