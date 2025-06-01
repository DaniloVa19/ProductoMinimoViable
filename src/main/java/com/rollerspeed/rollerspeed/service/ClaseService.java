package com.rollerspeed.rollerspeed.service;

import com.rollerspeed.rollerspeed.model.Clase;
import com.rollerspeed.rollerspeed.repository.ClaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClaseService {

    private final ClaseRepository claseRepository;

    @Autowired
    public ClaseService(ClaseRepository claseRepository) {
        this.claseRepository = claseRepository;
    }

    // Guardar o actualizar una clase
    public Clase guardarClase(Clase clase) {
        return claseRepository.save(clase);
    }

    // Listar todas las clases
    public List<Clase> listarClases() {
        return claseRepository.findAll();
    }

    // Obtener una clase por su ID
    public Optional<Clase> obtenerClasePorId(Long id) {
        return claseRepository.findById(id);
    }

    // Eliminar una clase
    public void eliminarClase(Long id) {
        claseRepository.deleteById(id);
    }

    // Puedes agregar más lógica de negocio según lo que necesites
}
