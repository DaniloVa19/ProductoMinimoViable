package com.rollerspeed.rollerspeed.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MenuController {
    @GetMapping("mision")
    public String mision() {
        return "mision";
    }

    @GetMapping("vision")
    public String vision() {
        return "vision";
    }

    @GetMapping("valores")
    public String valores() {
        return "valores";
    }

    @GetMapping("servicios")
    public String servicios() {
        return "servicios";
    }

    @GetMapping("eventos")
    public String eventos() {
        return "eventos";
    }

    @GetMapping("")
    public String home() {
        return "home";
    }
}
