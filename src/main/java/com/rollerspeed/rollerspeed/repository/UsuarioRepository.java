package com.rollerspeed.rollerspeed.repository;

import java.util.Optional;


import com.rollerspeed.rollerspeed.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    Optional<Usuario> findByEmail(String email);
}
