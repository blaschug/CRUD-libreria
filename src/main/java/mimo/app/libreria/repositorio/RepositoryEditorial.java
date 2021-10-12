package mimo.app.libreria.repositorio;

import mimo.app.libreria.entidades.Autor;
import mimo.app.libreria.entidades.Editorial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryEditorial extends JpaRepository<Editorial, Long> {

    Editorial findByNombreIgnoreCase(String nombre);
}
