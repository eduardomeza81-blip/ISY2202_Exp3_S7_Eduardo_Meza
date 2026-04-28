package com.duoc.seguridadcalidad;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController {

    @GetMapping({"/", "/home", "/recetas"})
    public String home(Model model) {
        model.addAttribute("titulo", "Recetas de cocina");
        return "home";
    }

    @GetMapping("/buscar")
    public String buscar(Model model) {
        model.addAttribute("titulo", "Buscar recetas");
        return "buscar";
    }

    @GetMapping("/detalle/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        model.addAttribute("idReceta", id);
        return "detalle";
    }

/*    @GetMapping("/login")
    public String login() {
        return "login";
    }*/ 
}