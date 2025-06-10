package com.rollerspeed.rollerspeed.controller;

import com.rollerspeed.rollerspeed.model.Clase;
import com.rollerspeed.rollerspeed.model.Instructor;
import com.rollerspeed.rollerspeed.service.ClaseService;
import com.rollerspeed.rollerspeed.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Clases", description = "Operaciones sobre manejo de clases")
@Controller
@RequestMapping("/clases")
public class ClaseController {

    private final ClaseService claseService;
    private final InstructorService instructorService;

    @Autowired
    public ClaseController(ClaseService claseService, InstructorService instructorService) {
        this.claseService = claseService;
        this.instructorService = instructorService;
    }

    @Operation(
    summary = "Muestra formulario para agregar clase",
    responses = {
        @ApiResponse(responseCode = "200", description = "Vista formulario clase")
    }
    )
    @GetMapping("/nueva")
    public String mostrarFormularioClase(Model model) {
        model.addAttribute("clase", new Clase());
        model.addAttribute("instructores", instructorService.obtenerTodos()); // para selector de instructores
        return "clase_form";  // nombre de la plantilla Thymeleaf
    }

    // Mostrar formulario para editar clase existente
    @Operation(
    summary = "Muestra formulario para editar clase",
    responses = {
        @ApiResponse(responseCode = "200", description = "Vista formulario edición clase"),
        @ApiResponse(responseCode = "400", description = "ID de clase inválido")
    }
    )
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model model) {
        Clase clase = claseService.obtenerClasePorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Clase inválida: " + id));
        model.addAttribute("clase", clase);
        model.addAttribute("instructores", instructorService.obtenerTodos());
        return "clase_form";  // reutiliza la plantilla del formulario
    }

    // Guardar o actualizar clase
    @Operation(
    summary = "Guarda o actualiza una clase",
    responses = {
        @ApiResponse(responseCode = "302", description = "Redirige a la lista de clases")
    }
    )
    @PostMapping("/guardar")
    public String guardarClase(@ModelAttribute("clase") Clase clase) {
        // Obtener el ID del instructor enviado desde el formulario
        Long instructorId = clase.getInstructor().getId();

        // Buscar el instructor completo por su ID
        Instructor instructor = instructorService.obtenerPorId(instructorId)
                .orElseThrow(() -> new IllegalArgumentException("Instructor inválido: " + instructorId));

        // Asignar el objeto Instructor completo a la clase
        clase.setInstructor(instructor);

        // Guardar la clase (nuevo o actualización)
        claseService.guardarClase(clase);

        return "redirect:/clases/lista";
    }

    // Listar todas las clases
    @Operation(
    summary = "Lista todas las clases",
    description = "Muestra la vista con la lista de clases",
    responses = {
        @ApiResponse(responseCode = "200", description = "Vista lista clases mostrada correctamente")
    })
    @GetMapping("/lista")
    public String listarClases(Model model) {
        model.addAttribute("clases", claseService.listarClases());
        return "clase_lista";  // plantilla para mostrar la lista
    }

    @Operation(
    summary = "Elimina una clase",
    responses = {
        @ApiResponse(responseCode = "302", description = "Redirige a la lista de clases")
    }
    )
    @DeleteMapping("/eliminar/{id}")
    public String eliminarClase(@PathVariable Long id) {
        claseService.eliminarClase(id);
        return "redirect:/clases/lista";
    }

    @PutMapping("/actualizar/{id}")
    public String actualizarClase(@PathVariable Long id, @ModelAttribute("clase") Clase clase) {
    Clase claseExistente = claseService.obtenerClasePorId(id)
            .orElseThrow(() -> new IllegalArgumentException("Clase inválida: " + id));

    claseExistente.setNombre(clase.getNombre());
    claseExistente.setNivel(clase.getNivel());
    claseExistente.setHorario(clase.getHorario());

    Long instructorId = clase.getInstructor().getId();
    Instructor instructor = instructorService.obtenerPorId(instructorId)
            .orElseThrow(() -> new IllegalArgumentException("Instructor inválido: " + instructorId));
    claseExistente.setInstructor(instructor);

    claseService.guardarClase(claseExistente);

    return "redirect:/clases/lista";
}

}
