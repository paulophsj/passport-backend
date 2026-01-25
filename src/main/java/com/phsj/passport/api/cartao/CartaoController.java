package com.phsj.passport.api.cartao;

import com.phsj.passport.api.cartao.dto.AlterarCartaoDTO;
import com.phsj.passport.api.cartao.dto.CriarCartaoDTO;
import com.phsj.passport.model.cartao.Cartao;
import com.phsj.passport.model.cartao.CartaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/cartao")
public class CartaoController {
    @Autowired
    private CartaoService cartaoService;

    @GetMapping
    public ResponseEntity<List<Cartao>> listarCartoes(){
        List<Cartao> cartoes = cartaoService.listarCartoes();
        return new ResponseEntity<List<Cartao>>(cartoes, HttpStatus.OK);
    }
    @PostMapping("/{id}")
    public ResponseEntity<Cartao> criarCartao(@PathVariable("id") Long id, @RequestBody @Valid CriarCartaoDTO cartao){
        Cartao novoCartao = cartaoService.criarCartao(id, cartao.build());
        return new ResponseEntity<Cartao>(novoCartao, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Cartao> alterarCartao(@PathVariable("id") Long id, @RequestBody @Valid AlterarCartaoDTO cartao){
        Cartao cartaoAlterado = cartaoService.alterarCartao(id, cartao.build());
        return new ResponseEntity<Cartao>(cartaoAlterado, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerCartao(@PathVariable("id") Long id){
        cartaoService.removerCartao(id);
        return ResponseEntity.ok().build();
    }
}
