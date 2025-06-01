package com.rollerspeed.rollerspeed.repository;

import com.rollerspeed.rollerspeed.model.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Long> {
    // Puedes agregar métodos personalizados aquí si necesitas buscar por instructor, nivel, etc.
}

