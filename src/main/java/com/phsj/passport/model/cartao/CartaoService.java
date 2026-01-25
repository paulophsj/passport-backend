package com.phsj.passport.model.cartao;

import com.phsj.passport.model.usuario.Usuario;
import com.phsj.passport.model.usuario.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class CartaoService {
    @Autowired
    private CartaoRepository cartaoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Cartao> listarCartoes() {
        List<Cartao> cartoes = cartaoRepository.findAll();
        return cartoes;
    }

    @Transactional
    public Cartao criarCartao(Long usuario_id, Cartao cartao) {
        Usuario usuarioExistente = usuarioRepository.findById(usuario_id).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado."));
        cartao.setUsuario(usuarioExistente);
        cartao.setHabilitado(Boolean.TRUE);
        return cartaoRepository.save(cartao);
    }

    @Transactional
    public Cartao alterarCartao(Long id, Cartao cartao) {
        Cartao cartaoExistente = cartaoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cartão não encontrado."));

        if (cartao.getStatus() != null) {
            cartaoExistente.setStatus(cartao.getStatus());
        }

        if (cartao.getTipoCartao() != null) {
            cartaoExistente.setTipoCartao(cartao.getTipoCartao());
        }

        if (StringUtils.hasText(cartao.getNome())) {
            cartaoExistente.setNome(cartao.getNome());
        }

        return cartaoRepository.save(cartaoExistente);
    }

    public void removerCartao(Long id) {
        Cartao cartaoExistente = cartaoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cartão não encontrado."));
        cartaoExistente.setHabilitado(Boolean.FALSE);
        cartaoRepository.save(cartaoExistente);
    }
}
