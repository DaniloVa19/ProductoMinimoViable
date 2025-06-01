package com.rollerspeed.rollerspeed.service;

import com.rollerspeed.rollerspeed.model.MetodoPago;
import com.rollerspeed.rollerspeed.repository.MetodoPagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MetodoPagoService {

    private final MetodoPagoRepository metodoPagoRepository;

    @Autowired
    public MetodoPagoService(MetodoPagoRepository metodoPagoRepository) {
        this.metodoPagoRepository = metodoPagoRepository;
    }

    public List<MetodoPago> listarMetodosPago() {
        return metodoPagoRepository.findAll();
    }

    public MetodoPago guardarMetodoPago(MetodoPago metodoPago) {
        return metodoPagoRepository.save(metodoPago);
    }

    public Optional<MetodoPago> obtenerMetodoPagoPorId(Long id) {
        return metodoPagoRepository.findById(id);
    }

    public void eliminarMetodoPago(Long id) {
        metodoPagoRepository.deleteById(id);
    }
}
