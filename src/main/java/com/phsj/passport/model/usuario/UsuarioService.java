package com.phsj.passport.model.usuario;

import com.phsj.passport.model.acesso.Perfil;
import com.phsj.passport.model.acesso.PerfilRepository;
import com.phsj.passport.util.enums.TiposPerfil;
import com.phsj.passport.util.security.JwtService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PerfilRepository perfilRepository;

    public UsuarioService(UsuarioRepository usuarioRepository, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, JwtService jwtService, PerfilRepository perfilRepository) {
        this.usuarioRepository = usuarioRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.perfilRepository = perfilRepository;
    }

    public Usuario obterUsuarioLogado(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);
            String userEmail = jwtService.extractUsername(jwt);
            return encontrarPeloEmail(userEmail);
        }
        return null;
    }

    public Usuario authenticate(String email, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        Usuario user = this.encontrarPeloEmail(email);
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o email: " + email));
    }

    public Usuario encontrarPeloEmail(String email) {
        return usuarioRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado."));
    }

    //GET, POST, PUT, DELETE

    public List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios;
    }

    @Transactional
    public Usuario criarUsuario(Usuario usuario, HttpServletRequest request) {
        boolean existsEmail = this.usuarioRepository.existsByEmail(usuario.getEmail());

        if (existsEmail) {
            throw new EntityExistsException("Já existe um usuário com o email cadastrado.");
        }

        Usuario usuarioLogado = this.obterUsuarioLogado(request);

        if (usuarioLogado == null || usuarioLogado.getRoles().stream().anyMatch(user -> user.getNome().equals(Perfil.ROLE_ADMIN))) {
            usuario.setRoles(Arrays.asList(new Perfil(Perfil.ROLE_CLIENTE)));
        } else {
            usuario.setRoles(Arrays.asList(new Perfil(Perfil.ROLE_ADMIN)));
        }

        for (Perfil perfil : usuario.getRoles()) {
            perfil.setHabilitado(Boolean.TRUE);
            perfilRepository.save(perfil);
        }

        usuario.setHabilitado(Boolean.TRUE);
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario alterarUsuario(Long id, Usuario dados, TiposPerfil role) {

        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado."));

        if (StringUtils.hasText(dados.getEmail()) && !dados.getEmail().equals(usuario.getEmail())) {

            if (usuarioRepository.existsByEmail(dados.getEmail())) {
                throw new EntityExistsException("Já existe um usuário com o email cadastrado.");
            }

            usuario.setEmail(dados.getEmail());
        }

        if (StringUtils.hasText(dados.getNome())) {
            usuario.setNome(dados.getNome());
        }

        if (StringUtils.hasText(dados.getSenha())) {

            if (dados.getSenha().length() < 6) {
                throw new ValidationException("A senha deve ter no mínimo 6 caracteres.");
            }

            usuario.setSenha(passwordEncoder.encode(dados.getSenha()));
        }

        if (role != null) {
            List<Perfil> perfisUsuario = this.perfilRepository.findAllByUserId(usuario.getId());
            System.out.println(perfisUsuario);
            for(Perfil perfil: perfisUsuario){
                perfil.setNome(role.getTipo());
                perfilRepository.save(perfil);
            }
        }

        return usuarioRepository.save(usuario);
    }

    public void removerUsuario(Long id) {
        Usuario usuarioExistente = usuarioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado."));
        usuarioExistente.setHabilitado(Boolean.FALSE);
        usuarioRepository.save(usuarioExistente);
    }
}
