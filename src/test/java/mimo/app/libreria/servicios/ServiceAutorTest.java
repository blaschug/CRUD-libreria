package mimo.app.libreria.servicios;

import mimo.app.libreria.LibreriaApplication;
import mimo.app.libreria.entidades.Autor;
import mimo.app.libreria.exepciones.AutorRepositoryException;
import mimo.app.libreria.repositorio.RepositoryAutor;
import mimo.app.libreria.repositorio.RepositoryLibro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(classes = LibreriaApplication.class)
public class ServiceAutorTest {

    @Autowired
    private ServiceAutor serviceAutor;

    @Autowired
    private RepositoryAutor repositoryAutor;

    @Autowired
    private RepositoryLibro repositoryLibro;

    @Test
    public void buscarPorNombre_conNombreExistente_retornaAutores() {
        String nombre = "Blas";

        Autor autor = serviceAutor.buscarPorNombre(nombre);

        assertThat(autor.getNombre()).isEqualTo(nombre);
    }

    @Test
    public void buscarPorNombre_conNombreNoExistente_retornaNull() {
        String nombre = "Pedro";

        Autor autor = serviceAutor.buscarPorNombre(nombre);

        assertThat(autor).isNull();
    }

    @Test
    public void buscarPorId_conIdExistente_retornaAutor() {
        long id = 1L;

        Autor autor = serviceAutor.buscarPorId(id);

        assertThat(autor).isNotNull();
        assertThat(autor.getId()).isEqualTo(id);
    }

    @Test
    public void buscarPorId_conIdINoexistente_retornaNull() {
        long id = -1L;

        Autor autor = serviceAutor.buscarPorId(id);

        assertThat(autor).isNull();
    }

    @Test
    public void buscarTodos_existentes_retornaAutores() {
        List<Autor> autor = serviceAutor.buscarTodos();

        assertThat(autor).isNotEmpty();
    }

    @Test
    @Transactional
    public void buscarTodos_inexistentes_retornaNull() {
        repositoryLibro.deleteAll();
        repositoryAutor.deleteAll();

        List<Autor> autor = serviceAutor.buscarTodos();

        assertThat(autor).isNull();
    }

    @Test
    @Transactional
    public void guardaAutor_conNombreInexistente_esVoid() throws Exception {
        String nombre = "Juan";

        serviceAutor.guardar(nombre);

        assertThat(serviceAutor.buscarPorNombre("Juan")).isNotNull();
    }

    @Test
    public void guardaAutor_conNombreExistente_lanzaExepcion() {
        String nombre = "Blas";

        assertThatThrownBy(() -> {
            serviceAutor.guardar(nombre);
        }).isInstanceOf(AutorRepositoryException.class).hasMessageContaining("Ese nombre de autor ya existe");
    }

    @Test
    @Transactional
    public void modificaNombreAutor_conAutorExistente_esVoid() throws Exception {
        String nombre = "Blas";
        String nuevoNombre = "Ivan";

        serviceAutor.modificarNombre(nombre, nuevoNombre);

        assertThat(serviceAutor.buscarPorNombre(nuevoNombre)).isNotNull();
        assertThat(serviceAutor.buscarPorNombre(nombre)).isNull();
    }

    @Test
    public void modificarNombreAutor_conAutorInexistente_lanzaExepcion() {
        String nombre = "Blas";
        String nuevoNombre = "Ivan";

        assertThatThrownBy(() -> {
            serviceAutor.modificarNombre(nuevoNombre, nombre);
        }).isInstanceOf(AutorRepositoryException.class).hasMessageContaining("No existe el autor a modificar");

    }

    @Test
    @Transactional
    public void eliminarAutor_conAutorExistenteSinLibrosRelacionados_esVoid() throws Exception {
        serviceAutor.guardar("Pepito");

        serviceAutor.eliminar("Pepito");

        assertThat(serviceAutor.buscarPorNombre("Pepito")).isNull();
    }

    @Test
    public void eliminarAutor_conAutorExistenteConLibrosRelacionados_lanzaExepcion() {
        String nombre = "Blas";

        assertThatThrownBy(() -> {
            serviceAutor.eliminar(nombre);
        }).isInstanceOf(AutorRepositoryException.class).hasMessageContaining("No puede eliminar un autor con libros relacionados");
    }

    @Test
    public void darBajaAutor_conAutorEnAlta_esVoid() {


    }
}

