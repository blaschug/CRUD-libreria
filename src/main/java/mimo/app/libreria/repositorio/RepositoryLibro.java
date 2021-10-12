package mimo.app.libreria.repositorio;

import mimo.app.libreria.entidades.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositoryLibro extends JpaRepository<Libro, Long> {
    List<Libro> findByAutorNombreIgnoreCase(String nombre);

    List<Libro> findByNombreIgnoreCase(String titulo);

    List<Libro> findByEditorialNombreIgnoreCase(String nombre);

    List<Libro> findByIsbn(long isbn);

    List<Libro> findByAnio(int anio);
}
