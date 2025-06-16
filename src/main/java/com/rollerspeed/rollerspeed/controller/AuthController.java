package com.rollerspeed.rollerspeed.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String loginForm() {
        return "login"; // Retorna el template login.html
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard"; // Puedes crear dashboard.html para mostrar después de login
    }

    @GetMapping("/register")
    public String registerForm() {
        return "register"; // Crea luego este template si haces el registro
    }
}
