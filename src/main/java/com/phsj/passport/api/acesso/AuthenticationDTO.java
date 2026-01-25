package com.phsj.passport.api.acesso;

import jakarta.validation.constraints.NotBlank;

public class AuthenticationDTO {
    @NotBlank
    private String email;

    @NotBlank
    private String senha;

    //Getters e Setters

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getSenha() {
        return senha;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
