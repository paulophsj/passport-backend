package com.phsj.passport.util.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serializable;

@MappedSuperclass
public class EntidadeNegocio implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @JsonIgnore
    @Column
    private Boolean habilitado;

    protected EntidadeNegocio(){}

    public Long getId(){
        return id;
    }

    public void setHabilitado(Boolean habilitado){
        this.habilitado = habilitado;
    }

    public Boolean getHabilitado(){
        return habilitado;
    }
}
