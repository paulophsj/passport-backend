package com.phsj.passport.util.enums;

public enum TiposPerfil {
    CLIENTE("CLIENTE"),
    ADMIN("ADMIN");

    private final String tipo;

    TiposPerfil(String tipo){
        this.tipo = tipo;
    };

    public String getTipo(){
        return tipo;
    }
}
