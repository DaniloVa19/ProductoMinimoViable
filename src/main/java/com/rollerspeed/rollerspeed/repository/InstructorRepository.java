package com.rollerspeed.rollerspeed.repository;

import com.rollerspeed.rollerspeed.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Long> {
    // Puedes agregar métodos personalizados aquí si quieres
}
