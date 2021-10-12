package mimo.app.libreria.controllers;

import mimo.app.libreria.servicios.ServiceEditorial;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EditorialController {

    private final ServiceEditorial serviceEditorial;

    public EditorialController(ServiceEditorial serviceEditorial) {
        this.serviceEditorial = serviceEditorial;
    }

    @RequestMapping(value = "/buscar/editoriales")
    public String buscaAutores(Model model, @RequestParam(required = false, value = "nombre") String nombre) {

        if (nombre == null) {
            model.addAttribute("editoriales", serviceEditorial.buscarTodos());
        } else {
            model.addAttribute("editoriales", serviceEditorial.buscarPorNombre(nombre));
        }
        return "buscarEditoriales";
    }

    @GetMapping(value = "/cargar/editoriales")
    public String cargar() {
        return "cargarEditoriales";
    }

    @PostMapping(value = "/cargar/editoriales")
    public String cargarEditoriales(ModelMap model, @RequestParam String nombre) throws Exception {
        try {
            serviceEditorial.guardar(nombre);
        } catch (Exception e) {
            model.put("error", e.getMessage());
            return "cargarEditoriales";
        }

        return "cargadoSatisfactoriamente";
    }

    @GetMapping(value = "/modificar/editoriales")
    public String modificar() {
        return "modificarEditoriales";
    }

    @PostMapping(value = "/modificar/editoriales")
    public String modificarEditoriales(ModelMap model, @RequestParam String nombre, @RequestParam String nuevoNombre) throws Exception {
        try {
            serviceEditorial.modificarNombre(nombre, nuevoNombre);
        } catch (Exception e) {
            model.put("error", e.getMessage());
            return "modificarEditoriales";
        }

        return "modificadoSatisfactoriamente";
    }

    @GetMapping(value = "/eliminar/editoriales")
    public String eliminar() {
        return "eliminarEditoriales";
    }

    @PostMapping(value = "/eliminar/editoriales")
    public String eliminarEditoriales(ModelMap model, @RequestParam String nombre) throws Exception {
        try {
            serviceEditorial.eliminar(nombre);
        } catch (Exception e) {
            model.put("error", e.getMessage());
            return "eliminarEditoriales";
        }

        return "eliminadoSatisfactoriamente";
    }
}
