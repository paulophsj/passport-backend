package com.phsj.passport.model.cartao;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartaoRepository extends JpaRepository<Cartao, Long> {
    boolean existsByNumeroCartao(Integer numeroCartao);
}
