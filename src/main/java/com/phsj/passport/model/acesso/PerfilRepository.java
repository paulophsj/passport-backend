package com.phsj.passport.model.acesso;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {
    @Query("""
        SELECT p
        FROM Usuario u
        JOIN u.roles p
        WHERE u.id = :userId
    """)
    List<Perfil> findAllByUserId(@Param("userId") Long userId);
}
