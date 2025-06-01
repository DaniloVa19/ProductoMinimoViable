package com.rollerspeed.rollerspeed.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_metodos_pago")
public class MetodoPago {
@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String tipo; // Ej: "Tarjeta", "Transferencia", "Efectivo"
}
