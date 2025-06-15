package com.rollerspeed.rollerspeed.service;

import com.rollerspeed.rollerspeed.model.Instructor;
import com.rollerspeed.rollerspeed.model.Rol;
import com.rollerspeed.rollerspeed.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.List;
import java.util.Optional;

@Service
public class InstructorService {

    
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private InstructorRepository instructorRepository;


    @Autowired
    public InstructorService(InstructorRepository instructorRepository,PasswordEncoder passwordEncoder){
        this.passwordEncoder=passwordEncoder;
        this.instructorRepository=instructorRepository;
    }

    // Guardar un nuevo instructor
    public Instructor guardarInstructor(Instructor instructor) {
        instructor.setRol(Rol.INSTRUCTOR);
        instructor.setPassword(passwordEncoder.encode(instructor.getPassword()));

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
