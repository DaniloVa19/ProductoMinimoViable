package com.rollerspeed.rollerspeed.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(name = "e_mail", nullable = false, length = 100, unique = true)
    private String email;

    @Column(nullable = false, length = 50)
    private String password;
}
