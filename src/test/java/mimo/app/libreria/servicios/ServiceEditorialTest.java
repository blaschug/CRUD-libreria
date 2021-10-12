package mimo.app.libreria.servicios;

import mimo.app.libreria.LibreriaApplication;
import mimo.app.libreria.entidades.Editorial;
import mimo.app.libreria.exepciones.EditorialRepositoryException;
import mimo.app.libreria.repositorio.RepositoryEditorial;
import mimo.app.libreria.repositorio.RepositoryLibro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(classes = LibreriaApplication.class)
public class ServiceEditorialTest {

    @Autowired
    private ServiceEditorial serviceEditorial;

    @Autowired
    private RepositoryEditorial repositoryEditorial;

    @Autowired
    private RepositoryLibro repositoryLibro;

    @Test
    public void buscarPorNombre_conNombreExistente_retornaEditorial() {
        String nombre = "Anagrama";

        Editorial editorial = serviceEditorial.buscarPorNombre(nombre);

        assertThat(editorial.getNombre()).isEqualTo(nombre);
    }

    @Test
    public void buscarPorNombre_conNombreInexistente_retornaNull() {
        String nombre = "Ravensburger";

        Editorial editorial = serviceEditorial.buscarPorNombre(nombre);

        assertThat(editorial).isNull();
    }

    @Test
    public void buscarPorId_conIdExistente_retornaEditorial() {
        long id = 1L;

        Editorial editorial = serviceEditorial.buscarPorId(id);

        assertThat(editorial).isNotNull();
        assertThat(editorial.getId()).isEqualTo(id);
    }

    @Test
    public void buscarPorId_conIdInexistente_retornaNull() {
        long id = -1L;

        Editorial editorial = serviceEditorial.buscarPorId(id);

        assertThat(editorial).isNull();
    }

    @Test
    public void buscarTodos_existentes_retornaEditoriales() {
        List<Editorial> editorial = serviceEditorial.buscarTodos();

        assertThat(editorial).isNotEmpty();
    }

    @Test
    @Transactional
    public void buscarTodos_inexistentes_retornaNull() {
        repositoryLibro.deleteAll();
        repositoryEditorial.deleteAll();


        List<Editorial> editorial = serviceEditorial.buscarTodos();


        assertThat(editorial).isNull();
    }

    @Test
    @Transactional
    public void guardaEditorial_conNombreInexistente_esVoid() throws Exception {
        String nombre = "Compaq";

        serviceEditorial.guardar(nombre);

        assertThat(serviceEditorial.buscarPorNombre(nombre)).isNotNull();
    }

    @Test
    public void guardaEditorial_conNombreExistente_lanzaExepcion() {
        String nombre = "Anagrama";

        assertThatThrownBy(() -> {
            serviceEditorial.guardar(nombre);
        }).isInstanceOf(EditorialRepositoryException.class).hasMessageContaining("El nombre de la editorial ya existe");
    }

    @Test
    @Transactional
    public void modificaNombreEditorial_conEditorialExistente_esVoid() throws Exception {
        String nombre = "Anagrama";
        String nuevoNombre = "Ledesma";

        serviceEditorial.modificarNombre(nombre, nuevoNombre);

        assertThat(serviceEditorial.buscarPorNombre(nuevoNombre)).isNotNull();
        assertThat(serviceEditorial.buscarPorNombre(nombre)).isNull();
    }

    @Test
    public void modificarNombreEditorial_conEditorialInexistente_lanzaExepcion() {
        String nombre = "Anagrama";
        String nuevoNombre = "Ledesma";

        assertThatThrownBy(() -> {
            serviceEditorial.modificarNombre(nuevoNombre, nombre);
        }).isInstanceOf(EditorialRepositoryException.class).hasMessageContaining("No existe la editorial a modificar");

    }

    @Test
    @Transactional
    public void eliminarEditorial_conEditorialExistenteSinLibrosRelacionados_esVoid() throws Exception {
        String nombre = "Ledesma";
        serviceEditorial.guardar(nombre);

        serviceEditorial.eliminar(nombre);

        assertThat(serviceEditorial.buscarPorNombre(nombre)).isNull();
    }

    @Test
    public void eliminarEditorial_conEditorialExistenteConLibrosRelacionados_lanzaExepcion() {
        String nombre = "Anagrama";

        assertThatThrownBy(() -> {
            serviceEditorial.eliminar(nombre);
        }).isInstanceOf(EditorialRepositoryException.class).hasMessageContaining("No puede eliminar una editorial con libros relacionados");
    }
}
