package mimo.app.libreria.servicios;

import mimo.app.libreria.entidades.Editorial;
import mimo.app.libreria.exepciones.EditorialRepositoryException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ServiceEditorial {

    Editorial buscarPorNombre(String nombre);

    Editorial buscarPorId(long id);

    List<Editorial> buscarTodos();

    void guardar(String nombre) throws Exception;

    void modificarNombre(String nombre, String nuevoNombre) throws Exception;

    void eliminar(String nombre) throws Exception;
}
