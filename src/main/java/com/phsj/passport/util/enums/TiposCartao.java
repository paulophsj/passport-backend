package com.phsj.passport.util.enums;

public enum TiposCartao {
    COMUM("Comum"),
    ESTUDANTE("Estudante"),
    TRABALHADOR("Trabalhador");

    private final String tipo;

    TiposCartao(String tipo){
        this.tipo = tipo;
    }

    public String getTipo(){
        return tipo;
    }
}
