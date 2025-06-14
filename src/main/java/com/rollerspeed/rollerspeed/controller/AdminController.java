package com.rollerspeed.rollerspeed.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/dashboard")
    public String mostrarDashboard(Model model) {
        model.addAttribute("titulo", "Bienvenido Administrador");
        return "admin_dashboard"; // Nombre del template
    }
}
