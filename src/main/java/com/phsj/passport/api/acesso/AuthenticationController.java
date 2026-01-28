package com.phsj.passport.api.acesso;

import com.phsj.passport.model.usuario.Usuario;
import com.phsj.passport.model.usuario.UsuarioService;
import com.phsj.passport.util.security.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public ResponseEntity<Usuario> check(HttpServletRequest request){
        Usuario usuarioLogado = usuarioService.obterUsuarioLogado(request);
        return new ResponseEntity<Usuario>(usuarioLogado, HttpStatus.OK);
    }

    @GetMapping("/check-token")
    public Map<Object, Object> checkToken(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        Map<Object, Object> checkAuthResponse = new HashMap<>();

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);
            String userEmail = jwtService.extractUsername(jwt);
            UserDetails userDetails = usuarioService.loadUserByUsername(userEmail);

            boolean isValid = jwtService.isTokenValid(jwt, userDetails);

            checkAuthResponse.put("isValid", isValid ? true : false);
            return checkAuthResponse;
        }

        checkAuthResponse.put("isValid", "Credenciais inválidas.");
        return checkAuthResponse;
    }
}
