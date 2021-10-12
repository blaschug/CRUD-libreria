package mimo.app.libreria.servicios.impl;

import mimo.app.libreria.entidades.Libro;
import mimo.app.libreria.exepciones.LibroRepositoryExeption;
import mimo.app.libreria.repositorio.RepositoryLibro;
import mimo.app.libreria.servicios.ServiceAutor;
import mimo.app.libreria.servicios.ServiceEditorial;
import mimo.app.libreria.servicios.ServiceLibro;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceLibroImpl implements ServiceLibro {

    private final ServiceAutor serviceAutor;
    private final ServiceEditorial serviceEditorial;
    private final RepositoryLibro repositoryLibro;

    public ServiceLibroImpl(RepositoryLibro repositoryLibro, ServiceAutor serviceAutor,
                            ServiceEditorial serviceEditorial) {
        this.repositoryLibro = repositoryLibro;
        this.serviceAutor = serviceAutor;
        this.serviceEditorial = serviceEditorial;
    }

    @Override
    public Libro buscaPorId(long id) throws LibroRepositoryExeption {
        Optional<Libro> libro = repositoryLibro.findById(id);
        if (libro.isEmpty()) {
            throw new LibroRepositoryExeption("No se encuentra un libro con ese ID");
        }
        return libro.get();
    }

    @Override
    public List<Libro> buscarPorNombreDeAutor(String nombre) {
        List<Libro> libros = repositoryLibro.findByAutorNombreIgnoreCase(nombre);
        return libros.isEmpty() ? null : libros;
    }

    @Override
    public List<Libro> buscarPorTitulo(String titulo) {
        List<Libro> libros = repositoryLibro.findByNombreIgnoreCase(titulo);
        return libros.isEmpty() ? null : libros;
    }

    @Override
    public List<Libro> buscarPorNombreDeEditorial(String nombre) {
        List<Libro> libros = repositoryLibro.findByEditorialNombreIgnoreCase(nombre);
        return libros.isEmpty() ? null : libros;
    }

    @Override
    public List<Libro> buscarTodos() {
        List<Libro> libros = repositoryLibro.findAll();
        return libros.isEmpty() ? null : libros;
    }

    @Override
    public List<Libro> buscaPorIsbn(long isbn) {
        List<Libro> libros = repositoryLibro.findByIsbn(isbn);
        return libros.isEmpty() ? null : libros;
    }

    @Override
    public List<Libro> buscaPorAnio(int anio) {
        List<Libro> libros = repositoryLibro.findByAnio(anio);
        return libros.isEmpty() ? null : libros;
    }

    @Override
    public void guardar(long isbn, String titulo, int anio, int ejemplares, int ejemplaresPrestados, String autor,
                        String editorial) throws LibroRepositoryExeption {
        validaLibro(isbn, titulo, anio, ejemplares, ejemplaresPrestados, autor, editorial);
        repositoryLibro.save(new Libro(isbn, titulo, anio, ejemplares, ejemplaresPrestados,
                ejemplares - ejemplaresPrestados, serviceAutor.buscarPorNombre(autor),
                serviceEditorial.buscarPorNombre(editorial)));
    }

    @Override
    public void modificar(Libro libro, Long isbnNuevo, String tituloNuevo, Integer anioNuevo, Integer
            ejemplaresNuevo,
                          Integer ejemplaresPrestadosNuevo, String nombreAutorNuevo, String nombreEditorialNuevo) throws Exception {

        if (libro == null) {
            throw new LibroRepositoryExeption("No se encontro un Libro con esa ID");
        } else {
            long isbn = isbnNuevo == null ? libro.getIsbn() : isbnNuevo;
            String titulo = tituloNuevo.equals("") ? libro.getNombre() : tituloNuevo;
            int anio = anioNuevo == null ? libro.getAnio() : anioNuevo;
            int ejemplares = ejemplaresNuevo == null ? libro.getEjemplares() : ejemplaresNuevo;
            int ejemplaresPrestados = ejemplaresPrestadosNuevo == null ? libro.getEjemplaresPrestados() : ejemplaresPrestadosNuevo;
            String autor = serviceAutor.buscarPorNombre(nombreAutorNuevo) == null ? libro.getAutor().getNombre() : nombreAutorNuevo;
            String editorial = serviceEditorial.buscarPorNombre(nombreEditorialNuevo) == null ? libro.getEditorial().getNombre() : nombreEditorialNuevo;


            validaLibro(isbnNuevo, tituloNuevo, anio, ejemplares, ejemplaresPrestados, autor, editorial);


            libro.setIsbn(isbn);
            libro.setNombre(titulo);
            libro.setAnio(anio);
            libro.setEjemplares(ejemplares);
            libro.setEjemplaresPrestados(ejemplaresPrestados);
            libro.setAutor(serviceAutor.buscarPorNombre(autor));
            libro.setEditorial(serviceEditorial.buscarPorNombre(editorial));
            repositoryLibro.save(libro);
        }
    }

    @Override
    public void eliminar(long id) throws LibroRepositoryExeption {
        repositoryLibro.delete(buscaPorId(id));
    }

    private void validaLibro(Long isbn, String titulo, int anio, int ejemplares, int ejemplaresPrestados,
                             String autor, String editorial) throws LibroRepositoryExeption {
        if (isbn != null) {
            if (buscaPorIsbn(isbn) != null) {
                throw new LibroRepositoryExeption("El ISBN ingresado existe para el libro " + buscaPorIsbn(isbn).get(0).getNombre());
            }
        }
        if (titulo != null) {
            if (buscarPorTitulo(titulo) != null) {
                throw new LibroRepositoryExeption("Ya existe un libro con el titulo ingresado");
            }
        }

        if (serviceAutor.buscarPorNombre(autor) == null) {
            throw new LibroRepositoryExeption("El Autor ingresado no existe");
        }
        if (serviceEditorial.buscarPorNombre(editorial) == null) {
            throw new LibroRepositoryExeption("La editorial ingresada no existe");
        }
        if (ejemplares < ejemplaresPrestados) {
            throw new LibroRepositoryExeption("No puede prestar mas libros de los que posee");
        }
        if (anio < 0) {
            throw new LibroRepositoryExeption("No se puede ingresar un anio negativo");
        }

    }


}
