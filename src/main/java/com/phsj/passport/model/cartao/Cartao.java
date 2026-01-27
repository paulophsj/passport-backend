package com.phsj.passport.model.cartao;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.phsj.passport.model.usuario.Usuario;
import com.phsj.passport.util.entity.EntidadeAuditavel;
import com.phsj.passport.util.enums.TiposCartao;
import jakarta.persistence.*;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "cartao")
@SQLRestriction("habilitado = true")
public class Cartao extends EntidadeAuditavel {
    @Column(unique = true, nullable = false)
    private Integer numeroCartao;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @JsonProperty("usuarioId")
    public Long getUsuarioId() {
        return usuario != null ? usuario.getId() : null;
    }

    @Column(nullable = false)
    private TiposCartao tipoCartao;

    public Cartao(){}

    public Integer getNumeroCartao() {
        return numeroCartao;
    }

    public Cartao setNumeroCartao(Integer numeroCartao) {
        this.numeroCartao = numeroCartao;
        return this;
    }

    public String getNome() {
        return nome;
    }

    public Cartao setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public Cartao setStatus(Boolean status) {
        this.status = status;
        return this;
    }

    public Boolean getStatus() {
        return status;
    }

    public Cartao setTipoCartao(TiposCartao tipoCartao) {
        this.tipoCartao = tipoCartao;
        return this;
    }

    public TiposCartao getTipoCartao() {
        return tipoCartao;
    }

    public Cartao setUsuario(Usuario usuario){
        this.usuario = usuario;
        return this;
    }
}
