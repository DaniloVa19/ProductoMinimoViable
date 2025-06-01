package com.rollerspeed.rollerspeed.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_clases")
public class Clase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 50)
    private String nivel;

    @Column(nullable = false)
    private String horario;

    // Relación con Instructor: Muchas clases pueden ser impartidas por un instructor
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id", nullable = false)
    @ToString.Exclude  // Excluimos para evitar problemas en toString()
    private Instructor instructor;

    // Relación con Alumnos: Una clase tiene muchos alumnos inscritos
    @ManyToMany(mappedBy = "clases", fetch = FetchType.LAZY)
    @ToString.Exclude  // Excluimos para evitar problemas en toString()
    private List<Alumno> alumnos = new ArrayList<>();
}
