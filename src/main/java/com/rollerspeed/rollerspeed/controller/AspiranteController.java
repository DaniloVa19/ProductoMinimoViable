package com.rollerspeed.rollerspeed.controller;

import com.rollerspeed.rollerspeed.model.Aspirante;
import com.rollerspeed.rollerspeed.model.Alumno;
import com.rollerspeed.rollerspeed.model.MetodoPago;
import com.rollerspeed.rollerspeed.model.Rol;
import com.rollerspeed.rollerspeed.service.AspiranteService;
import com.rollerspeed.rollerspeed.service.AlumnoService;
import com.rollerspeed.rollerspeed.service.MetodoPagoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Tag(name = "Aspirantes", description = "Operaciones para gestionar aspirantes y convertirlos en alumnos")
@Controller
@RequestMapping("/aspirantes")
public class AspiranteController {

    @Autowired
    private AspiranteService aspiranteService;

    @Autowired
    private MetodoPagoService metodoPagoService;

    @Autowired
    private AlumnoService alumnoService;

    @Operation(summary = "Mostrar formulario de registro de aspirante", description = "Devuelve el formulario para registrar un nuevo aspirante junto con los métodos de pago disponibles.", responses = {
            @ApiResponse(responseCode = "200", description = "Formulario de registro mostrado correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/nuevo")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("aspirante", new Aspirante());
        List<MetodoPago> metodosPago = metodoPagoService.listarMetodosPago();
        model.addAttribute("metodosPago", metodosPago);
        return "aspirante_form";
    }

    @Operation(summary = "Guardar aspirante", description = "Guarda un nuevo aspirante en la base de datos y redirige a la lista de aspirantes.", responses = {
            @ApiResponse(responseCode = "302", description = "Redirige a la lista de aspirantes"),
            @ApiResponse(responseCode = "500", description = "Error interno al guardar el aspirante")
    })
    @PostMapping("/guardar")
    public String guardarAspirante(
            @ModelAttribute("aspirante") Aspirante aspirante,
            @RequestParam("foto") MultipartFile archivoFoto) {
        aspirante.setRol(Rol.ASPIRANTE);

        try {
            aspiranteService.guardarAspirante(aspirante, archivoFoto);
        } catch (IOException e) {
            e.printStackTrace(); // puedes loguear o manejar mejor el error
            return "redirect:/aspirantes/nuevo?error=archivo";
        }

        return "redirect:/aspirantes/lista";
    }

    @Operation(summary = "Listar aspirantes", description = "Muestra una lista de todos los aspirantes registrados.", responses = {
            @ApiResponse(responseCode = "200", description = "Lista de aspirantes mostrada correctamente", content = @Content(schema = @Schema(implementation = Aspirante.class))),
            @ApiResponse(responseCode = "500", description = "Error interno al obtener la lista")
    })
    @GetMapping("/lista")
    public String listarAspirantes(Model model) {
        model.addAttribute("aspirantes", aspiranteService.listarAspirantes());
        return "aspirante_lista";
    }

    @Operation(summary = "Convertir aspirante a alumno", description = "Convierte un aspirante en alumno, copiando sus datos y eliminando al aspirante.", responses = {
            @ApiResponse(responseCode = "302", description = "Redirige a la lista de alumnos o aspirantes según el resultado"),
            @ApiResponse(responseCode = "404", description = "Aspirante no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno al convertir aspirante")
    })
    @GetMapping("/convertir/{id}")
    public String convertirAAlumno(@PathVariable Long id) {
        Optional<Aspirante> aspiranteOpt = aspiranteService.obtenerAspirantePorId(id);

        if (aspiranteOpt.isPresent()) {
            Aspirante aspirante = aspiranteOpt.get();

            // Primero eliminar el aspirante para liberar el email único
            aspiranteService.eliminarAspirante(id);

            // Crear alumno nuevo y copiar datos desde aspirante
            Alumno alumno = new Alumno();
            alumno.setNombre(aspirante.getNombre());
            alumno.setEmail(aspirante.getEmail());
            alumno.setTelefono(aspirante.getTelefono());
            alumno.setPassword(aspirante.getPassword());
            alumno.setFotoUrl(aspirante.getFotoUrl());
            alumno.setRol(Rol.ALUMNO);

            alumnoService.guardarAlumno(alumno);

            return "redirect:/alumnos/lista";
        } else {
            return "redirect:/aspirantes/lista";
        }
    }
}
