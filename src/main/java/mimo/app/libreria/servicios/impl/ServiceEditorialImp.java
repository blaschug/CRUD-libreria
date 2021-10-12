package mimo.app.libreria.servicios.impl;

import mimo.app.libreria.entidades.Editorial;
import mimo.app.libreria.exepciones.EditorialRepositoryException;
import mimo.app.libreria.repositorio.RepositoryEditorial;
import mimo.app.libreria.servicios.ServiceEditorial;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceEditorialImp implements ServiceEditorial {

    private final RepositoryEditorial repositoryEditorial;

    public ServiceEditorialImp(RepositoryEditorial repositoryEditorial) {
        this.repositoryEditorial = repositoryEditorial;
    }

    @Override
    public Editorial buscarPorNombre(String nombre) {
        return repositoryEditorial.findByNombreIgnoreCase(nombre);
    }

    @Override
    public Editorial buscarPorId(long id) {
        return repositoryEditorial.findById(id).orElse(null);
    }

    @Override
    public List<Editorial> buscarTodos() {
        List<Editorial> editoriales = repositoryEditorial.findAll();
        return editoriales.isEmpty() ? null : editoriales;
    }

    @Override
    public void guardar(String nombre) throws EditorialRepositoryException {
        if (validarEditorial(nombre)) {
            throw new EditorialRepositoryException("El nombre de la editorial ya existe");
        }
        repositoryEditorial.save(new Editorial(nombre));
    }

    @Override
    public void modificarNombre(String nombre, String nuevoNombre) throws EditorialRepositoryException {
        if (!validarEditorial(nombre)) {
            throw new EditorialRepositoryException("No existe la editorial a modificar");
        }
        Editorial editorial = buscarPorNombre(nombre);
        editorial.setNombre(nuevoNombre);
        repositoryEditorial.save(editorial);
    }

    @Override
    public void eliminar(String nombre) throws EditorialRepositoryException {
        if (validarEditorial(nombre)) {
            try {
                repositoryEditorial.delete(buscarPorNombre(nombre));
            } catch (Exception e) {
                throw new EditorialRepositoryException("No puede eliminar una editorial con libros relacionados");
            }
        } else {
            throw new EditorialRepositoryException("No existe esa editorial");
        }
    }

    private boolean validarEditorial(String nombre) {
        return buscarPorNombre(nombre) != null;
    }
}
