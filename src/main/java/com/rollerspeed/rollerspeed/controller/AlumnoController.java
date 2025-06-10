package com.rollerspeed.rollerspeed.controller;

import com.rollerspeed.rollerspeed.service.AlumnoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.rollerspeed.rollerspeed.model.Alumno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Tag(
    name = "Alumnos",
    description = "Operaciones relacionadas con la visualización de alumnos registrados en el sistema"
)
@Controller
@RequestMapping("/alumnos")
public class AlumnoController {

    private final AlumnoService alumnoService;

    @Autowired
    public AlumnoController(AlumnoService alumnoService) {
        this.alumnoService = alumnoService;
    }

    @Operation(
        summary = "Mostrar lista de alumnos",
        description = "Obtiene una lista de todos los alumnos registrados desde la base de datos y la pasa al modelo para ser mostrada en una vista HTML.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Vista HTML renderizada exitosamente con la lista de alumnos"
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Error interno al recuperar la lista de alumnos"
            )
        }
    )
    @GetMapping("/lista")
    public String listarAlumnos(Model model) {
        List<Alumno> alumnos = alumnoService.listarAlumnos();
        model.addAttribute("alumnos", alumnos);
        return "alumno_lista";
    }
}
