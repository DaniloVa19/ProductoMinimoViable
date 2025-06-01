package com.rollerspeed.rollerspeed.service;

import com.rollerspeed.rollerspeed.model.Alumno;
import com.rollerspeed.rollerspeed.repository.AlumnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlumnoService {

    private final AlumnoRepository alumnoRepository;

    @Autowired
    public AlumnoService(AlumnoRepository alumnoRepository) {
        this.alumnoRepository = alumnoRepository;
    }

    // Guardar o actualizar alumno
    public Alumno guardarAlumno(Alumno alumno) {
        return alumnoRepository.save(alumno);
    }

    // Listar todos los alumnos
    public List<Alumno> listarAlumnos() {
        return alumnoRepository.findAll();
    }

    // Obtener alumno por ID
    public Optional<Alumno> obtenerAlumnoPorId(Long id) {
        return alumnoRepository.findById(id);
    }

    // Eliminar alumno
    public void eliminarAlumno(Long id) {
        alumnoRepository.deleteById(id);
    }
}
