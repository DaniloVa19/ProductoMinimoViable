package com.rollerspeed.rollerspeed.controller;

import com.rollerspeed.rollerspeed.model.Usuario;
import com.rollerspeed.rollerspeed.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Tag(
    name = "Usuarios",
    description = "Operaciones para la gestión de usuarios registrados en el sistema"
)
@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Obtener todos los usuarios
@Operation(
        summary = "Mostrar lista de usuarios registrados",
        description = "Obtiene todos los usuarios registrados desde la base de datos, los añade al modelo y renderiza una vista HTML (usuarios.html) con la información.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Vista HTML renderizada con la lista de usuarios",
                content = @Content(schema = @Schema(implementation = Usuario.class))
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Error interno al recuperar la lista de usuarios"
            )
        }
    ) 
    @GetMapping(value = "/listar")
    public String getAllUsuarios(Model model) {
        List<Usuario> listaUsuarios = usuarioService.getUsuarios();
        model.addAttribute("listaUsarios", listaUsuarios);
        return "usuarios";
    }

    
}
