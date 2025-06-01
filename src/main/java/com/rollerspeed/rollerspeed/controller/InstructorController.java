package com.rollerspeed.rollerspeed.controller;

import com.rollerspeed.rollerspeed.model.Instructor;
import com.rollerspeed.rollerspeed.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/instructores")
public class InstructorController {

    @Autowired
    private InstructorService instructorService;

    // Mostrar formulario para crear un nuevo instructor
    @GetMapping("/nuevo")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("instructor", new Instructor());
        return "instructor_form";  // Nombre del archivo .html (plantilla Thymeleaf)
    }

    // Mostrar formulario para editar un instructor existente
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

    // Procesar el formulario de registro y actualización
    @PostMapping("/guardar")
    public String guardarInstructor(@ModelAttribute("instructor") Instructor instructor) {
        instructorService.guardarInstructor(instructor);
        return "redirect:/instructores/lista";
    }

    // Mostrar lista de instructores
    @GetMapping("/lista")
    public String listarInstructores(Model model) {
        model.addAttribute("instructores", instructorService.obtenerTodos());
        return "instructor_lista";  // Nombre de la plantilla HTML para mostrar lista
    }

    // Método para eliminar instructor usando POST
    @PostMapping("/eliminar/{id}")
    public String eliminarInstructor(@PathVariable Long id) {
        instructorService.eliminarPorId(id);
        return "redirect:/instructores/lista";
    }
}
