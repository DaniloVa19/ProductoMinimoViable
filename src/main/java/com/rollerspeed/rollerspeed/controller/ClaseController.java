package com.rollerspeed.rollerspeed.controller;

import com.rollerspeed.rollerspeed.model.Clase;
import com.rollerspeed.rollerspeed.model.Instructor;
import com.rollerspeed.rollerspeed.service.ClaseService;
import com.rollerspeed.rollerspeed.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    // Mostrar formulario para crear nueva clase
    @GetMapping("/nueva")
    public String mostrarFormularioClase(Model model) {
        model.addAttribute("clase", new Clase());
        model.addAttribute("instructores", instructorService.obtenerTodos()); // para selector de instructores
        return "clase_form";  // nombre de la plantilla Thymeleaf
    }

    // Mostrar formulario para editar clase existente
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model model) {
        Clase clase = claseService.obtenerClasePorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Clase inválida: " + id));
        model.addAttribute("clase", clase);
        model.addAttribute("instructores", instructorService.obtenerTodos());
        return "clase_form";  // reutiliza la plantilla del formulario
    }

    // Guardar o actualizar clase
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
    @GetMapping("/lista")
    public String listarClases(Model model) {
        model.addAttribute("clases", claseService.listarClases());
        return "clase_lista";  // plantilla para mostrar la lista
    }

    // Eliminar clase por ID
    @GetMapping("/eliminar/{id}")
    public String eliminarClase(@PathVariable Long id) {
        claseService.eliminarClase(id);
        return "redirect:/clases/lista";
    }
}
