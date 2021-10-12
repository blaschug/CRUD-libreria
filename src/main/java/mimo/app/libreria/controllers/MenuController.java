package mimo.app.libreria.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MenuController {

    @GetMapping(value = "/")
    public String index() {
        return "index";
    }

    @GetMapping(value = "/buscar")
    public String buscar() {
        return "buscar";
    }

    @GetMapping(value = "/cargar")
    public String cargar() {
        return "cargar";
    }

    @GetMapping(value = "/modificar")
    public String modificar() {
        return "modificar";
    }

    @GetMapping(value = "/eliminar")
    public String eliminar() {
        return "eliminar";
    }
}
