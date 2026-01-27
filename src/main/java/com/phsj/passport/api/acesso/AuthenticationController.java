package com.phsj.passport.api.acesso;

import com.phsj.passport.model.usuario.Usuario;
import com.phsj.passport.model.usuario.UsuarioService;
import com.phsj.passport.util.security.JwtService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthenticationController {

    private final JwtService jwtService;

    private UsuarioService usuarioService;

    public AuthenticationController(JwtService jwtService, UsuarioService usuarioService) {

        this.jwtService = jwtService;
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public Map<Object, Object> signin(@RequestBody AuthenticationDTO data) {

        Usuario authenticatedUser = usuarioService.authenticate(data.getEmail(), data.getSenha());

        String jwtToken = jwtService.generateToken(authenticatedUser);

        Map<Object, Object> loginResponse = new HashMap<>();
        loginResponse.put("id", authenticatedUser.getId());
        loginResponse.put("role", authenticatedUser.getRoles());
        loginResponse.put("username", authenticatedUser.getNome());
        loginResponse.put("token", jwtToken);
        loginResponse.put("tokenExpiresIn", jwtService.getExpirationTime());

        return loginResponse;
    }
}
