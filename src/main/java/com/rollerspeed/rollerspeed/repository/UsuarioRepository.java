package com.rollerspeed.rollerspeed.repository;



import com.rollerspeed.rollerspeed.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

}
