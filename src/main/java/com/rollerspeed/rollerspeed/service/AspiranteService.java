package com.rollerspeed.rollerspeed.service;

import com.rollerspeed.rollerspeed.model.Aspirante;
import com.rollerspeed.rollerspeed.repository.AspiranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AspiranteService {

    private final AspiranteRepository aspiranteRepository;

    @Autowired
    public AspiranteService(AspiranteRepository aspiranteRepository) {
        this.aspiranteRepository = aspiranteRepository;
    }

    // Guardar o actualizar un aspirante
    public Aspirante guardarAspirante(Aspirante aspirante) {
        return aspiranteRepository.save(aspirante);
    }

    // Listar todos los aspirantes
    public List<Aspirante> listarAspirantes() {
        return aspiranteRepository.findAll();
    }

    // Obtener un aspirante por su ID
    public Optional<Aspirante> obtenerAspirantePorId(Long id) {
        return aspiranteRepository.findById(id);
    }

    // Eliminar un aspirante
    public void eliminarAspirante(Long id) {
        aspiranteRepository.deleteById(id);
    }

    // Puedes agregar lógica adicional aquí más adelante si es necesario
}
