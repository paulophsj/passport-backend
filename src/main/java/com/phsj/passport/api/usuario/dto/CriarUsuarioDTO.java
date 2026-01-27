package com.phsj.passport.api.usuario.dto;

import com.phsj.passport.model.usuario.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public class CriarUsuarioDTO {
    @NotBlank(message = "O campo nome é de preenchimento obrigatório")
    @Pattern(regexp = ".*\\S.*", message = "O nome não pode conter apenas espaços")
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ ]+$", message = "O nome deve conter apenas letras e espaços")
    private String nome;

    @Email(message = "O campo email deve seguir o formato padrão.")
    @NotBlank(message = "O campo email é de preenchimento obrigatório.")
    private String email;

    @NotBlank(message = "O campo senha é de preenchimento obrigatório.")
    @Length(min = 6, message = "O campo senha deve ter no mínimo {min} caracteres.")
    private String senha;

    @NotBlank(message = "O campo confirmarSenha é de preenchimento obrigatório.")
    @Length(min = 6, message = "O campo confirmarSenha deve ter no mínimo {min} caracteres.")
    private String confirmarSenha;

    public CriarUsuarioDTO() {}

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public String getConfirmarSenha() {
        return confirmarSenha;
    }

    public void setConfirmarSenha(String confirmarSenha) {
        this.confirmarSenha = confirmarSenha;
    }

    public Usuario build(){
        return new Usuario()
                .setNome(this.nome)
                .setEmail(this.email)
                .setSenha(this.senha);
    }
}
