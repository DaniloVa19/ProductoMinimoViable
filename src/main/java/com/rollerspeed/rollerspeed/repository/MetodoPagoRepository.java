package com.rollerspeed.rollerspeed.repository;

import com.rollerspeed.rollerspeed.model.MetodoPago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetodoPagoRepository extends JpaRepository<MetodoPago, Long> {
    // Aquí puedes agregar consultas personalizadas si las necesitas
}
