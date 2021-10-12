package mimo.app.libreria.servicios;

import mimo.app.libreria.LibreriaApplication;
import mimo.app.libreria.entidades.Libro;
import mimo.app.libreria.exepciones.LibroRepositoryExeption;
import mimo.app.libreria.repositorio.RepositoryLibro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(classes = LibreriaApplication.class)
public class ServiceLibroTest {

    @Autowired
    private ServiceLibro serviceLibro;

    @Autowired
    private RepositoryLibro repositoryLibro;

    @Test
    public void buscaPorId_conIdExistente_retornaLibro() throws LibroRepositoryExeption {
        long id = 1;

        Libro libro = serviceLibro.buscaPorId(id);

        assertThat(libro).isNotNull();
    }

    @Test
    public void buscaPorId_conIdNoExistente_retornaNull() throws LibroRepositoryExeption {
        long id = -1;

        Libro libro = serviceLibro.buscaPorId(id);

        assertThat(libro).isNull();
    }

    @Test
    public void buscarPorNombreDeAutor_conAutorExistente_retornaLibros() {
        String nombre = "Blas";

        List<Libro> libros = serviceLibro.buscarPorNombreDeAutor(nombre);

        assertThat(libros).isNotEmpty();
    }

    @Test
    public void buscarPorNombreDeAutor_conAutorInexistente_retornaNull() {
        String nombre = "Pedro";

        List<Libro> libros = serviceLibro.buscarPorNombreDeAutor(nombre);

        assertThat(libros).isNull();
    }

    @Test
    public void buscarPorTitulo_conTituloExistente_retornaLibros() {
        String titulo = "El Silmarillion";

        List<Libro> librosTitulo = serviceLibro.buscarPorTitulo(titulo);

        assertThat(librosTitulo).isNotEmpty();
    }

    @Test
    public void buscarPorTitulo_conTituloInexistente_retornaNull() {
        String titulo = "El Pistolero";

        List<Libro> librosTitulo = serviceLibro.buscarPorTitulo(titulo);

        assertThat(librosTitulo).isNull();
    }

    @Test
    public void buscarPorNombreDeEditorial_conEditorialExistente_retornaLibros() {
        String nombre = "Anagrama";

        List<Libro> libros = serviceLibro.buscarPorNombreDeEditorial(nombre);

        assertThat(libros).isNotEmpty();
    }

    @Test
    public void buscarPorNombreDeEditorial_conEditorialInexistente_retornaNull() {
        String nombre = "Patito Feo";

        List<Libro> libros = serviceLibro.buscarPorNombreDeEditorial(nombre);

        assertThat(libros).isNull();
    }

    @Test
    public void buscarTodos_conLibrosExistentes_retornaLibros() {
        List<Libro> libros = serviceLibro.buscarTodos();

        assertThat(libros).isNotEmpty();
    }

    @Test
    @Transactional
    public void buscarTodos_conLibrosInexistentes_retornaNull() {
        repositoryLibro.deleteAll();

        List<Libro> libros = serviceLibro.buscarTodos();

        assertThat(libros).isNull();
    }

    @Test
    public void buscarPorIsbn_conIsbnExistente_retornaLibros() {
        long isbn = 125;

        List<Libro> libros = serviceLibro.buscaPorIsbn(isbn);

        assertThat(libros).isNotEmpty();
    }

    @Test
    public void buscarPorIsbn_conIsbnInexistente_retornaNull() {
        long isbn = -1;

        List<Libro> libros = serviceLibro.buscaPorIsbn(isbn);

        assertThat(libros).isNull();
    }

    @Test
    public void buscarPorAnio_conAnioExistente_retornaLibros() {
        int anio = 1995;

        List<Libro> libros = serviceLibro.buscaPorAnio(anio);

        assertThat(libros).isNotEmpty();
    }

    @Test
    public void buscarPorAnio_conAnioInexistente_retornaNull() {
        int anio = -1;

        List<Libro> libros = serviceLibro.buscaPorAnio(anio);

        assertThat(libros).isNull();
    }

    @Test
    @Transactional
    public void guardaLibro_conTituloIsbnInexistentesAutorYEditorialExistente_esVoid() throws Exception {
        long isbn = 158;
        String titulo = "La comunidad del anillo";
        int anio = 1954;
        int ejemplares = 10;
        int ejemplaresPrestados = 5;
        String autor = "Blas";
        String editorial = "Anagrama";

        serviceLibro.guardar(isbn, titulo, anio, ejemplares, ejemplaresPrestados, autor, editorial);

        assertThat(serviceLibro.buscarPorTitulo("La comunidad del anillo")).isNotNull();
    }

    @Test
    @Transactional
    public void guardaLibro_conIsbnExistente_lanzaExepcion() {
        long isbn = 125;
        String titulo = "La comunidad del anillo";
        int anio = 1954;
        int ejemplares = 10;
        int ejemplaresPrestados = 5;
        String autor = "Blas";
        String editorial = "Anagrama";

        assertThatThrownBy(() -> {
            serviceLibro.guardar(isbn, titulo, anio, ejemplares, ejemplaresPrestados, autor, editorial);
        }).isInstanceOf(LibroRepositoryExeption.class).hasMessageContaining("El ISBN ingresado existe para el libro " + serviceLibro.buscaPorIsbn(isbn).get(0).getNombre());
    }

    @Test
    @Transactional
    public void guardaLibro_conTituloExistente_lanzaExepcion() {
        long isbn = 150;
        String titulo = "El Silmarillion";
        int anio = 1954;
        int ejemplares = 10;
        int ejemplaresPrestados = 5;
        String autor = "Blas";
        String editorial = "Anagrama";

        assertThatThrownBy(() -> {
            serviceLibro.guardar(isbn, titulo, anio, ejemplares, ejemplaresPrestados, autor, editorial);
        }).isInstanceOf(LibroRepositoryExeption.class).hasMessageContaining("Ya existe un libro con el titulo " +
                "ingresado");
    }

    @Test
    @Transactional
    public void guardaLibro_conAutorInexistente_lanzaExepcion() {
        long isbn = 150;
        String titulo = "Las Dos Torres";
        int anio = 1954;
        int ejemplares = 10;
        int ejemplaresPrestados = 5;
        String autor = "Pepito";
        String editorial = "Anagrama";

        assertThatThrownBy(() -> {
            serviceLibro.guardar(isbn, titulo, anio, ejemplares, ejemplaresPrestados, autor, editorial);
        }).isInstanceOf(LibroRepositoryExeption.class).hasMessageContaining("El Autor ingresado no existe");
    }

    @Test
    @Transactional
    public void guardaLibro_conEditorialInexistente_lanzaExepcion() {
        long isbn = 150;
        String titulo = "Las Dos Torres";
        int anio = 1954;
        int ejemplares = 10;
        int ejemplaresPrestados = 5;
        String autor = "Blas";
        String editorial = "Ledesma";

        assertThatThrownBy(() -> {
            serviceLibro.guardar(isbn, titulo, anio, ejemplares, ejemplaresPrestados, autor, editorial);
        }).isInstanceOf(LibroRepositoryExeption.class).hasMessageContaining("La editorial ingresada no existe");
    }

    @Test
    @Transactional
    public void guardaLibro_conMasEjemplaresPrestadosQueEjempalres_lanzaExepcion() {
        long isbn = 150;
        String titulo = "Las Dos Torres";
        int anio = 1954;
        int ejemplares = 10;
        int ejemplaresPrestados = 20;
        String autor = "Blas";
        String editorial = "Anagrama";

        assertThatThrownBy(() -> {
            serviceLibro.guardar(isbn, titulo, anio, ejemplares, ejemplaresPrestados, autor, editorial);
        }).isInstanceOf(LibroRepositoryExeption.class).hasMessageContaining("No puede prestar mas libros de los que " +
                "posee");
    }

    @Test
    @Transactional
    public void modificaLibro_conLibroExistenteYParametrosValidos_esVoid() throws Exception {
        long id = 1;
        long isbn = 150;
        String titulo = "La bestia";
        int anio = 1980;
        int ejemplares = 50;
        int ejemplaresPrestados = 20;
        String autor = "Blas";
        String editorial = "anagrama";

        serviceLibro.modificar(serviceLibro.buscaPorId(id), isbn, titulo, anio, ejemplares, ejemplaresPrestados,
                autor, editorial);

        assertThat(serviceLibro.buscarPorTitulo("La bestia")).isNotEmpty();
        assertThat(serviceLibro.buscaPorAnio(1980)).isNotEmpty();
        assertThat(serviceLibro.buscaPorIsbn(150)).isNotEmpty();
    }

    @Test
    @Transactional
    public void modificaLibro_conLibroInexistente_lanzaError() throws Exception {
        long id = 5;
        long isbn = 150;
        String titulo = "La bestia";
        int anio = 1980;
        int ejemplares = 50;
        int ejemplaresPrestados = 20;
        String autor = "Blas";
        String editorial = "anagrama";

        assertThatThrownBy(() -> {
            serviceLibro.modificar(serviceLibro.buscaPorId(id), isbn, titulo, anio, ejemplares,
                    ejemplaresPrestados, autor, editorial);
        }).isInstanceOf(LibroRepositoryExeption.class).hasMessageContaining("No se encontro un Libro con esa ID");
    }
}



























