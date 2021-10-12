package mimo.app.libreria.servicios;

import mimo.app.libreria.entidades.Autor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ServiceAutor {

    Autor buscarPorNombre(String string);

    Autor buscarPorId(long id);

    List<Autor> buscarTodos();

    void guardar(String nombre) throws Exception;

    void modificarNombre(String nombre, String nuevoNombre) throws Exception;

    void eliminar(String nombre) throws Exception;
}
