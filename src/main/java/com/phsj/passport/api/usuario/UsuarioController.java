package com.phsj.passport.api.usuario;

import com.phsj.passport.api.usuario.dto.AlterarUsuarioDTO;
import com.phsj.passport.api.usuario.dto.CriarUsuarioDTO;
import com.phsj.passport.model.usuario.Usuario;
import com.phsj.passport.model.usuario.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios(){
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Usuario> criarUsuario(@RequestBody @Valid CriarUsuarioDTO usuario, HttpServletRequest request){
        if(!usuario.getConfirmarSenha().equals(usuario.getSenha())){
            throw new RuntimeException("As senhas não coincidem.");
        }
        Usuario novoUsuario = usuarioService.criarUsuario(usuario.build(), request);
        return new ResponseEntity<Usuario>(novoUsuario, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> alterarUsuario(@RequestBody @Valid AlterarUsuarioDTO usuario, @PathVariable("id") Long id){
        Usuario usuarioAlterado = usuarioService.alterarUsuario(id, usuario.build(), usuario.getRole());
        return new ResponseEntity<Usuario>(usuarioAlterado, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerUsuario(@PathVariable("id") Long id){
        usuarioService.removerUsuario(id);
        return ResponseEntity.ok().build();
    }
}
