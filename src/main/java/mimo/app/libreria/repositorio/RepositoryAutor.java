package mimo.app.libreria.repositorio;

import mimo.app.libreria.entidades.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryAutor extends JpaRepository<Autor, Long> {

    Autor findByNombreIgnoreCase(String nombre);

}
