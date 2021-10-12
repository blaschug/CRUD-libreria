package mimo.app.libreria.servicios.impl;

import mimo.app.libreria.entidades.Autor;
import mimo.app.libreria.exepciones.AutorRepositoryException;
import mimo.app.libreria.repositorio.RepositoryAutor;
import mimo.app.libreria.servicios.ServiceAutor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceAutorImpl implements ServiceAutor {

    private final RepositoryAutor repositoryAutor;

    public ServiceAutorImpl(RepositoryAutor repositoryAutor) {
        this.repositoryAutor = repositoryAutor;
    }

    @Override
    public Autor buscarPorNombre(String string) {
        return repositoryAutor.findByNombreIgnoreCase(string);
    }

    @Override
    public Autor buscarPorId(long id) {
        return repositoryAutor.findById(id).orElse(null);
    }

    @Override
    public List<Autor> buscarTodos() {
        List<Autor> autores = repositoryAutor.findAll();
        return autores.isEmpty() ? null : autores;
    }

    @Override
    public void guardar(String nombre) throws AutorRepositoryException {
        if (validaAutor(nombre)) {
            throw new AutorRepositoryException("Ese nombre de autor ya existe");
        }
        repositoryAutor.save(new Autor(nombre));
    }

    @Override
    public void modificarNombre(String nombre, String nuevonombre) throws AutorRepositoryException {
        if (!validaAutor(nombre)) {
            throw new AutorRepositoryException("No existe el autor a modificar");
        }
        Autor autor = buscarPorNombre(nombre);
        autor.setNombre(nuevonombre);
        repositoryAutor.save(autor);
    }

    @Override
    public void eliminar(String nombre) throws AutorRepositoryException {
        if (validaAutor(nombre)) {
            try {
                repositoryAutor.delete(buscarPorNombre(nombre));
            } catch (Exception e) {
                throw new AutorRepositoryException("No puede eliminar un autor con libros relacionados");
            }
        } else {
            throw new AutorRepositoryException("No existe ese autor");
        }
    }

    private boolean validaAutor(String nombre) {
        return buscarPorNombre(nombre) != null;
    }
}
