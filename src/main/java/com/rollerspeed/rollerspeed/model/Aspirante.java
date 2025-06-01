package com.rollerspeed.rollerspeed.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_aspirantes")
@EqualsAndHashCode(callSuper = true)
public class Aspirante extends Usuario {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "metodo_pago_id", nullable = false)
    private MetodoPago metodoPago;

    // Otros campos si los hay
}
