package com.phsj.passport.api.cartao.dto;

import com.phsj.passport.model.cartao.Cartao;
import com.phsj.passport.model.usuario.Usuario;
import com.phsj.passport.util.enums.TiposCartao;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class AlterarCartaoDTO {
    @Size(min = 1, message = "O nome não pode estar vazio")
    @Pattern(regexp = ".*\\S.*", message = "O nome não pode conter apenas espaços")
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ ]+$", message = "O nome deve conter apenas letras e espaços")
    private String nome;

    private Boolean status;

    private TiposCartao tiposCartao;

    public AlterarCartaoDTO() {}

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Boolean getStatus() { return status; }
    public void setStatus(Boolean status) { this.status = status; }

    public TiposCartao getTiposCartao() { return tiposCartao; }
    public void setTiposCartao(TiposCartao tiposCartao) { this.tiposCartao = tiposCartao; }

    public Cartao build(){
        return new Cartao()
                .setTipoCartao(tiposCartao)
                .setNome(nome)
                .setStatus(status);
    }
}
