package com.rollerspeed.rollerspeed.controller;

import com.rollerspeed.rollerspeed.model.MetodoPago;
import com.rollerspeed.rollerspeed.service.MetodoPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/metodos_pago")
public class MetodoPagoController {

    private final MetodoPagoService metodoPagoService;

    @Autowired
    public MetodoPagoController(MetodoPagoService metodoPagoService) {
        this.metodoPagoService = metodoPagoService;
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevoMetodoPago(Model model) {
        model.addAttribute("medioPago", new MetodoPago());
        return "medio_pago_form";
    }

    @PostMapping("/guardar")
    public String guardarMetodoPago(@ModelAttribute("medioPago") MetodoPago metodoPago) {
        metodoPagoService.guardarMetodoPago(metodoPago);
        return "redirect:/metodos_pago/lista";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        MetodoPago metodoPago = metodoPagoService.obtenerMetodoPagoPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Método de pago no encontrado: " + id));
        model.addAttribute("medioPago", metodoPago);
        return "medio_pago_form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarMetodoPago(@PathVariable Long id) {
        metodoPagoService.eliminarMetodoPago(id);
        return "redirect:/metodos_pago/lista";
    }

    @GetMapping("/lista")
    public String listarMetodosPago(Model model) {
        model.addAttribute("mediosPago", metodoPagoService.listarMetodosPago());
        return "medio_pago_lista";
    }
}
