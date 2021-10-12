package mimo.app.libreria.controllers;

import mimo.app.libreria.servicios.ServiceAutor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
public class AutorController {

    private final ServiceAutor serviceAutor;

    public AutorController(ServiceAutor serviceAutor) {
        this.serviceAutor = serviceAutor;
    }

    @RequestMapping(value = "/buscar/autores")
    public String buscaAutores(Model model, @RequestParam(required = false, value = "nombre") String nombreaut) {

        if (nombreaut == null) {
            model.addAttribute("autores", serviceAutor.buscarTodos());
        } else {
            model.addAttribute("autores", serviceAutor.buscarPorNombre(nombreaut));
        }
        return "buscarAutores";
    }

    @GetMapping(value = "/cargar/autores")
    public String cargar() {
        return "cargarAutores";
    }

    @PostMapping(value = "/cargar/autores")
    public String cargarAutores(ModelMap model, @RequestParam String nombre) {
        try {
            serviceAutor.guardar(nombre);
        } catch (Exception e) {
            model.put("error", e.getMessage());
            return "cargarAutores";
        }

        return "cargadoSatisfactoriamente";
    }

    @GetMapping(value = "/modificar/autores")
    public String modificar() {
        return "modificarAutores";
    }

    @PostMapping(value = "/modificar/autores")
    public String modificarAutores(ModelMap model, @RequestParam String nombre, @RequestParam String nuevoNombre) {
        try {
            serviceAutor.modificarNombre(nombre, nuevoNombre);
        } catch (Exception e) {
            model.put("error", e.getMessage());
            return "modificarAutores";
        }

        return "modificadoSatisfactoriamente";
    }

    @GetMapping(value = "/eliminar/autores")
    public String eliminar() {
        return "eliminarAutores";
    }

    @PostMapping(value = "/eliminar/autores")
    public String eliminarAutores(ModelMap model, @RequestParam String nombre) {
        try {
            serviceAutor.eliminar(nombre);
        } catch (Exception e) {
            model.put("error", e.getMessage());
            return "eliminarAutores";
        }

        return "eliminadoSatisfactoriamente";
    }
}
