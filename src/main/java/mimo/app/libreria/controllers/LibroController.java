package mimo.app.libreria.controllers;

import mimo.app.libreria.entidades.Libro;
import mimo.app.libreria.exepciones.LibroRepositoryExeption;
import mimo.app.libreria.servicios.ServiceLibro;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
public class LibroController {

    private final ServiceLibro serviceLibro;

    public LibroController(ServiceLibro serviceLibro) {
        this.serviceLibro = serviceLibro;
    }

    @GetMapping("/buscar/libros")
    public String buscarLibros(Model model, @RequestParam(required = false) String atributos,
            @RequestParam(required = false) String buscador) {

        if (atributos != null) {
            if (atributos.equals("isbn")) {
                model.addAttribute("libros", serviceLibro.buscaPorIsbn(Long.parseLong(buscador)));
                return "buscarLibros";
            }
            if (atributos.equals("titulo")) {
                model.addAttribute("libros", serviceLibro.buscarPorTitulo(buscador));
                return "buscarLibros";
            }
            if (atributos.equals("anio")) {
                model.addAttribute("libros", serviceLibro.buscaPorAnio(Integer.parseInt(buscador)));
                return "buscarLibros";
            }
            if (atributos.equals("autor")) {
                model.addAttribute("libros", serviceLibro.buscarPorNombreDeAutor(buscador));
                return "buscarLibros";
            }
            if (atributos.equals("editorial")) {
                model.addAttribute("libros", serviceLibro.buscarPorNombreDeEditorial(buscador));
                return "buscarLibros";
            }
        }

        model.addAttribute("libros", serviceLibro.buscarTodos());
        return "buscarLibros";
    }

    @GetMapping(value = "/cargar/libros")
    public String cargar() {
        return "cargarLibros";
    }

    @PostMapping(value = "/cargar/libros")
    public String cargarAutores(ModelMap model, @RequestParam long isbn, @RequestParam String nombre,
            @RequestParam int anio, @RequestParam int ejemplares, @RequestParam int ejemplaresPrestados,
            @RequestParam String autor, @RequestParam String editorial) {
        try {
            serviceLibro.guardar(isbn, nombre, anio, ejemplares, ejemplaresPrestados, autor, editorial);
        } catch (Exception e) {
            model.put("error", e.getMessage());
            mantieneDatosDeInputs(model, isbn, nombre, anio, ejemplares, ejemplaresPrestados, autor, editorial);
            return "cargarLibros";
        }

        return "cargadoSatisfactoriamente";
    }

    // @GetMapping(value = "/modificar/libros")
    // public String modificar(ModelMap model) {
    // model.addAttribute("libros", serviceLibro.buscarTodos());
    // return "modificarLibros";
    // }

    @PostMapping(value = "/modificar/libros")
    public String modificarAutores(ModelMap model, @RequestParam(value = "id", required = true) Long id,
            @RequestParam(required = false) Long nuevoIsbn, @RequestParam(required = false) String nuevoNombre,
            @RequestParam(required = false) Integer nuevoAnio, @RequestParam(required = false) Integer nuevoEjemplares,
            @RequestParam(required = false) Integer nuevoEjemplaresPrestados,
            @RequestParam(required = false) String nuevoAutor, @RequestParam(required = false) String nuevoEditorial)
            throws LibroRepositoryExeption {

        try {
            Libro libro = serviceLibro.buscaPorId(id);
            serviceLibro.modificar(libro, nuevoIsbn, nuevoNombre, nuevoAnio, nuevoEjemplares, nuevoEjemplaresPrestados,
                    nuevoAutor, nuevoEditorial);
        } catch (Exception e) {
            model.put("error", e.getMessage());
            model.put("id", id);
            model.put("libro", serviceLibro.buscaPorId(id));
            mantieneDatosDeInputs(model, nuevoIsbn, nuevoNombre, nuevoAnio, nuevoEjemplares, nuevoEjemplaresPrestados,
                    nuevoAutor, nuevoEditorial);
            return "modificarLibrosId";
        }

        return "cargadoSatisfactoriamente";
    }

    @GetMapping(value = "/modificar/libros")
    public String modificarId(@RequestParam(value = "id", required = false) String id, ModelMap model)
            throws LibroRepositoryExeption {
        try {
            serviceLibro.buscaPorId(Long.parseLong(id));
        } catch (Exception e) {
            if (id != null) {
                model.put("error", e.getMessage());
                model.put("id", id);
            }
            return "modificarLibros";
        }
        model.put("libro", serviceLibro.buscaPorId(Long.parseLong(id)));
        model.put("id", id);
        return "modificarLibrosId";
    }

    @GetMapping(value = "/eliminar/libros")
    public String eliminar() {
        return "eliminarLibros";
    }

    @PostMapping(value = "/eliminar/libros")
    public String eliminarAutores(ModelMap model, @RequestParam Long id) {
        try {
            serviceLibro.eliminar(id);
        } catch (Exception e) {
            model.put("error", e.getMessage());
            return "eliminarAutores";
        }

        return "eliminadoSatisfactoriamente";
    }

    private void mantieneDatosDeInputs(ModelMap model, Long isbn, String nombre, Integer anio, Integer ejemplares,
            Integer ejemplaresPrestados, String autor, String editorial) {
        model.put("isbn", isbn);
        model.put("nombre", nombre);
        model.put("anio", anio);
        model.put("ejemplares", ejemplares);
        model.put("ejemplaresPrestados", ejemplaresPrestados);
        model.put("autor", autor);
        model.put("editorial", editorial);
    }

}
