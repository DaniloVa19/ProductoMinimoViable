package com.rollerspeed.rollerspeed.controller;

import com.rollerspeed.rollerspeed.model.MetodoPago;
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

@Controller
@RequestMapping("/metodos_pago")
@Tag(name = "Metodos de Pago", description = "Controlador para gestionar metodos de pago")
public class MetodoPagoController {

    private final MetodoPagoService metodoPagoService;

    @Autowired
    public MetodoPagoController(MetodoPagoService metodoPagoService) {
        this.metodoPagoService = metodoPagoService;
    }

    @Operation(
        summary = "Mostrar formulario para crear nuevo metodo de pago",
        description = "Carga el formulario HTML que permite al usuario ingresar datos para un nuevo metodo de pago.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Formulario mostrado correctamente",
                content = @Content(schema = @Schema(implementation = MetodoPago.class))),
            @ApiResponse(responseCode = "500", description = "Error al cargar el formulario")
        }
    )
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevoMetodoPago(Model model) {
        model.addAttribute("medioPago", new MetodoPago());
        return "medio_pago_form";
    }

    @Operation(
        summary = "Guardar un metodo de pago",
        description = "Recibe los datos del formulario y guarda un nuevo metodo de pago en la base de datos.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Metodo de pago guardado correctamente",
                content = @Content(schema = @Schema(implementation = MetodoPago.class))),
            @ApiResponse(responseCode = "500", description = "Error al guardar el metodo de pago")
        }
    )
    @PostMapping("/guardar")
    public String guardarMetodoPago(@ModelAttribute("medioPago") MetodoPago metodoPago) {
        metodoPagoService.guardarMetodoPago(metodoPago);
        return "redirect:/metodos_pago/lista";
    }

    @Operation(
        summary = "Mostrar formulario para editar metodo de pago",
        description = "Carga el formulario con los datos existentes de un metodo de pago para poder ser editado.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Formulario de edicion mostrado correctamente",
                content = @Content(schema = @Schema(implementation = MetodoPago.class))),
            @ApiResponse(responseCode = "404", description = "Metodo de pago no encontrado")
        }
    )
    @PutMapping("/editar/{id}")
    public String actualizarMetodoPago(@PathVariable Long id, @ModelAttribute("medioPago") MetodoPago metodoPago) {
        MetodoPago metodoExistente = metodoPagoService.obtenerMetodoPagoPorId(id)
            .orElseThrow(() -> new IllegalArgumentException("Método de pago no encontrado: " + id));

        metodoExistente.setTipo(metodoPago.getTipo());
        metodoPagoService.guardarMetodoPago(metodoExistente);

        return "redirect:/metodos_pago/lista";
    }

    @Operation(
        summary = "Eliminar metodo de pago",
        description = "Elimina un metodo de pago segun su ID. Redirige a la lista una vez completado.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Metodo de pago eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Metodo de pago no encontrado")
        }
    )
    @DeleteMapping("/eliminar/{id}")
    public String eliminarMetodoPago(@PathVariable Long id) {
    metodoPagoService.eliminarMetodoPago(id);
    return "redirect:/metodos_pago/lista";
    }


    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
    MetodoPago metodoPago = metodoPagoService.obtenerMetodoPagoPorId(id)
            .orElseThrow(() -> new IllegalArgumentException("Método de pago no encontrado: " + id));
    model.addAttribute("medioPago", metodoPago);
    return "medio_pago_form";
    }

    @Operation(
        summary = "Listar todos los metodos de pago",
        description = "Obtiene todos los metodos de pago registrados en la base de datos y los muestra en una vista HTML.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente",
                content = @Content(schema = @Schema(implementation = MetodoPago.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
        }
    )
    @GetMapping("/lista")
    public String listarMetodosPago(Model model) {
        model.addAttribute("mediosPago", metodoPagoService.listarMetodosPago());
        return "medio_pago_lista";
    }
}
