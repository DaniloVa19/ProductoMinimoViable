package com.rollerspeed.rollerspeed.controller;

import com.rollerspeed.rollerspeed.model.Instructor;
import com.rollerspeed.rollerspeed.model.Rol;
import com.rollerspeed.rollerspeed.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Optional;

@Tag(
    name = "Instructores",
    description = "API para gestión de instructores: creación, edición, listado y eliminación"
)
@Controller
@RequestMapping("/instructores")
public class InstructorController {

    @Autowired
    private InstructorService instructorService;

    // Mostrar formulario para crear un nuevo instructor
    @Operation(
        summary = "Mostrar formulario para crear un nuevo instructor",
        description = "Devuelve la vista del formulario para registrar un nuevo instructor.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Formulario de creación mostrado correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno al mostrar el formulario")
        }
    )
    @GetMapping("/nuevo")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("instructor", new Instructor());
        return "instructor_form";  // Nombre del archivo .html (plantilla Thymeleaf)
    }

    // Mostrar formulario para editar un instructor existente
    @Operation(
        summary = "Mostrar formulario para editar un instructor existente",
        description = "Devuelve la vista del formulario con datos del instructor para edición.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Formulario de edición mostrado correctamente"),
            @ApiResponse(responseCode = "302", description = "Redirigido a la lista si el instructor no existe"),
            @ApiResponse(responseCode = "500", description = "Error interno al mostrar el formulario de edición")
        }
    )
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model model) {
        Optional<Instructor> instructorOpt = instructorService.obtenerPorId(id);
        if (instructorOpt.isPresent()) {
            model.addAttribute("instructor", instructorOpt.get());
            return "instructor_form";  // reutilizamos la misma plantilla para editar
        } else {
            // Si no existe el instructor con ese id, redirigimos a la lista
            return "redirect:/instructores/lista";
        }
    }

    @Operation(
        summary = "Actualizar datos de un instructor",
        description = "Actualiza los datos de un instructor existente y redirige a la lista.",
        responses = {
            @ApiResponse(responseCode = "302", description = "Instructor actualizado y redirigido a la lista"),
            @ApiResponse(responseCode = "500", description = "Error interno al actualizar instructor")
        }
    )
    @PutMapping("/editar/{id}")
    public String actualizarInstructor(@PathVariable Long id, @ModelAttribute("instructor") Instructor instructor) {
    instructor.setId(id);
    instructorService.guardarInstructor(instructor);
    return "redirect:/instructores/lista";
    }

    @Operation(
        summary = "Guardar instructor",
        description = "Guarda un nuevo instructor o actualiza uno existente y redirige a la lista.",
        responses = {
            @ApiResponse(responseCode = "302", description = "Instructor guardado y redirigido a la lista"),
            @ApiResponse(responseCode = "500", description = "Error interno al guardar instructor")
        }
    )
    @PostMapping("/guardar")
    public String guardarInstructor(@ModelAttribute("instructor") Instructor instructor) {
        
        instructorService.guardarInstructor(instructor);
        return "redirect:/instructores/lista";
    }

    @Operation(
        summary = "Listar instructores",
        description = "Devuelve una vista con la lista de todos los instructores registrados.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Lista de instructores mostrada correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno al cargar la lista")
        }
    )
    @GetMapping("/lista")
    public String listarInstructores(Model model) {
        model.addAttribute("instructores", instructorService.obtenerTodos());
        return "instructor_lista";  // Nombre de la plantilla HTML para mostrar lista
    }

    @Operation(
        summary = "Eliminar instructor",
        description = "Elimina un instructor por su ID y redirige a la lista de instructores.",
        responses = {
            @ApiResponse(responseCode = "302", description = "Instructor eliminado y redirigido a la lista"),
            @ApiResponse(responseCode = "500", description = "Error interno al eliminar instructor")
        }
    )
    @DeleteMapping("/eliminar/{id}")
    public String eliminarInstructor(@PathVariable Long id) {
        instructorService.eliminarPorId(id);
        return "redirect:/instructores/lista";
    }
}
