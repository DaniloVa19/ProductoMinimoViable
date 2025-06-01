package com.rollerspeed.rollerspeed.controller;

import com.rollerspeed.rollerspeed.model.Aspirante;
import com.rollerspeed.rollerspeed.model.Alumno;
import com.rollerspeed.rollerspeed.model.MetodoPago;
import com.rollerspeed.rollerspeed.service.AspiranteService;
import com.rollerspeed.rollerspeed.service.AlumnoService;
import com.rollerspeed.rollerspeed.service.MetodoPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/aspirantes")
public class AspiranteController {

    @Autowired
    private AspiranteService aspiranteService;

    @Autowired
    private MetodoPagoService metodoPagoService;

    @Autowired
    private AlumnoService alumnoService;

    @GetMapping("/nuevo")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("aspirante", new Aspirante());
        List<MetodoPago> metodosPago = metodoPagoService.listarMetodosPago();
        model.addAttribute("metodosPago", metodosPago);
        return "aspirante_form";
    }

    @PostMapping("/guardar")
    public String guardarAspirante(@ModelAttribute("aspirante") Aspirante aspirante) {
        aspiranteService.guardarAspirante(aspirante);
        return "redirect:/aspirantes/lista";
    }

    @GetMapping("/lista")
    public String listarAspirantes(Model model) {
        model.addAttribute("aspirantes", aspiranteService.listarAspirantes());
        return "aspirante_lista";
    }

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

            alumnoService.guardarAlumno(alumno);

            return "redirect:/alumnos/lista";
        } else {
            return "redirect:/aspirantes/lista";
        }
    }
}
