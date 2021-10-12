package mimo.app.libreria.servicios;

import mimo.app.libreria.entidades.Libro;
import mimo.app.libreria.exepciones.LibroRepositoryExeption;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ServiceLibro {

    Libro buscaPorId(long id) throws LibroRepositoryExeption;

    List<Libro> buscarPorNombreDeAutor(String nombre);

    List<Libro> buscarPorTitulo(String titulo);

    List<Libro> buscarPorNombreDeEditorial(String nombre);

    List<Libro> buscarTodos();

    List<Libro> buscaPorIsbn(long isbn);

    List<Libro> buscaPorAnio(int anio);

    void guardar(long isbn, String titulo, int anio, int ejemplares, int ejemplaresPrestados, String ombreAutor,
                 String nombreEditorial) throws Exception;

    void modificar(Libro libro, Long isbnNuevo, String tituloNuevo, Integer anioNuevo,
                   Integer ejemplaresNuevo,
                   Integer ejemplaresPrestadosNuevo, String nombreAutorNuevo, String nombreEditorialNuevo) throws Exception;

    void eliminar(long id) throws Exception;
}