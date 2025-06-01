package com.rollerspeed.rollerspeed.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_instructores")
@EqualsAndHashCode(callSuper = true, exclude = "clases")
@ToString(callSuper = true, exclude = "clases")  // Excluye 'clases' del toString
public class Instructor extends Usuario {

    @Column(nullable = false, length = 100)
    private String especialidad;

    // Un instructor puede dar muchas clases
    @OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Clase> clases = new ArrayList<>();
}
