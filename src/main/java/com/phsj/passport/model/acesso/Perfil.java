package com.phsj.passport.model.acesso;

import com.phsj.passport.util.entity.EntidadeNegocio;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "perfil")
@SQLRestriction("habilitado = true")
public class Perfil extends EntidadeNegocio implements GrantedAuthority {
    public static final String ROLE_CLIENTE = "CLIENTE";
    public static final String ROLE_ADMIN = "ADMIN";

    private String nome;

    protected Perfil(){}
    public Perfil(String ROLE){
        this.nome = ROLE;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public String getAuthority() {
        return this.nome;
    }
}
