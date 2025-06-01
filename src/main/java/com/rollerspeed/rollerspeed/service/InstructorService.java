package com.rollerspeed.rollerspeed.service;

import com.rollerspeed.rollerspeed.model.Instructor;
import com.rollerspeed.rollerspeed.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InstructorService {

    @Autowired
    private InstructorRepository instructorRepository;

    // Guardar un nuevo instructor
    public Instructor guardarInstructor(Instructor instructor) {
        return instructorRepository.save(instructor);
    }

    // Buscar todos los instructores
    public List<Instructor> obtenerTodos() {
        return instructorRepository.findAll();
    }

    // Buscar un instructor por ID
    public Optional<Instructor> obtenerPorId(Long id) {
        return instructorRepository.findById(id);
    }

    // Eliminar un instructor por ID
    public void eliminarPorId(Long id) {
        instructorRepository.deleteById(id);
    }

    // Otros métodos personalizados que necesites...

}
