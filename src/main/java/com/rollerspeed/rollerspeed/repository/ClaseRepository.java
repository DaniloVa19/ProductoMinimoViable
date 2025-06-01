package com.rollerspeed.rollerspeed.repository;

import com.rollerspeed.rollerspeed.model.Clase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClaseRepository extends JpaRepository<Clase, Long> {
    // Puedes agregar métodos personalizados aquí si necesitas buscar por instructor, nivel, etc.
}
