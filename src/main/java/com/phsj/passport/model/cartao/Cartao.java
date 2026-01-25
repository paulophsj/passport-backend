package com.phsj.passport.model.cartao;

import com.phsj.passport.model.usuario.Usuario;
import com.phsj.passport.util.entity.EntidadeAuditavel;
import com.phsj.passport.util.enums.TiposCartao;
import jakarta.persistence.*;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "cartao")
@SQLRestriction("habilitado = true")
public class Cartao extends EntidadeAuditavel {
    @Column
    private Integer numeroCartao;

    @Column
    private String nome;

    @Column
    private Boolean status;

    @ManyToOne
    private Usuario usuario;

    @Column
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
