package com.agenciaviagens.repository;

import com.agenciaviagens.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    // Busca usuario por username (pro login)
    Optional<Usuario> findByUsername(String username);
    
    // Verifica se já existe esse username
    boolean existsByUsername(String username);
}