package com.phsj.passport.model.usuario;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.phsj.passport.model.acesso.Perfil;
import com.phsj.passport.model.cartao.Cartao;
import com.phsj.passport.util.entity.EntidadeAuditavel;
import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuario")
@SQLRestriction("habilitado = true")
public class Usuario extends EntidadeAuditavel implements UserDetails {
    @Column
    private String nome;

    @Column
    private String email;

    @Column
    @JsonIgnore
    private String senha;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario")
    @Fetch(FetchMode.SUBSELECT)
    private List<Cartao> cartoes = new ArrayList<>();

    @JsonIgnore
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Perfil> roles = new ArrayList<>();

    public Usuario(){}

    // UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return email;
    }

    @JsonIgnore
    public String getPassword() {
        return senha;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return this.getHabilitado();
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public List<Perfil> getRoles() {
        return roles;
    }

    public void setRoles(List<Perfil> roles) {
        this.roles = roles;
    }

    public Usuario setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Usuario setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getSenha() {
        return senha;
    }

    public Usuario setSenha(String senha) {
        this.senha = senha;
        return this;
    }

    public List<Cartao> getCartoes() {
        return cartoes;
    }
}
