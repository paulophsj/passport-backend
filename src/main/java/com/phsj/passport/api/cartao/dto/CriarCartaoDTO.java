package com.phsj.passport.api.cartao.dto;

import com.phsj.passport.model.cartao.Cartao;
import com.phsj.passport.model.usuario.Usuario;
import com.phsj.passport.util.enums.TiposCartao;
import jakarta.validation.constraints.*;

import java.util.concurrent.ThreadLocalRandom;

public class CriarCartaoDTO {
    @NotBlank(message = "O campo nome é obrigatório")
    @Size(min = 1, message = "O nome não pode estar vazio")
    @Pattern(regexp = ".*\\S.*", message = "O nome não pode conter apenas espaços")
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ ]+$", message = "O nome deve conter apenas letras e espaços")
    private String nome;

    @NotNull(message = "O campo status é obrigatório")
    private Boolean status;

    @NotNull(message = "O campo tiposCartao é obrigatório")
    private TiposCartao tiposCartao;

    public CriarCartaoDTO(){}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public TiposCartao getTiposCartao() {
        return tiposCartao;
    }

    public void setTiposCartao(TiposCartao tiposCartao) {
        this.tiposCartao = tiposCartao;
    }

        public Cartao build(){

            int minValue = 100_000_000;
            int maxValue = 999_999_999;

            Integer numeroCartaoAleatorio = ThreadLocalRandom.current().nextInt(minValue, maxValue);

            return new Cartao()
                    .setNumeroCartao(numeroCartaoAleatorio)
                    .setTipoCartao(tiposCartao)
                    .setNome(nome)
                    .setStatus(status);
        }
}
