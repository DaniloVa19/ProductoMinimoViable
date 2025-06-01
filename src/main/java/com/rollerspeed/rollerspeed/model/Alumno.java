package com.rollerspeed.rollerspeed.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_alumnos")
@EqualsAndHashCode(callSuper = true)
public class Alumno extends Usuario {


    // Relación con clases (un alumno puede estar inscrito en muchas clases)
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
        name = "alumno_clase",
        joinColumns = @JoinColumn(name = "alumno_id"),
        inverseJoinColumns = @JoinColumn(name = "clase_id")
    )
    private List<Clase> clases = new ArrayList<>();
}
