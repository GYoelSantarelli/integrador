package integrador.service;

import integrador.entities.Categoria;
import integrador.exception.EntidadNoEncontradaException;

import java.util.ArrayList;
import java.util.List;

public class CategoriaService {

    private List<Categoria> categorias;

    public CategoriaService() {
        this.categorias = new ArrayList<>();
    }

    public void crearCategoria(Categoria categoria) {

        if (categoria == null) {
            throw new IllegalArgumentException(
                    "La categoría no puede ser nula."
            );
        }

        categorias.add(categoria);
    }

    public List<Categoria> listarCategorias() {

        List<Categoria> activas = new ArrayList<>();

        for (Categoria categoria : categorias) {

            if (!categoria.isEliminado()) {
                activas.add(categoria);
            }
        }

        return activas;
    }

    public Categoria buscarPorId(Long id)
            throws EntidadNoEncontradaException {

        if (id == null) {
            throw new IllegalArgumentException(
                    "El ID no puede ser nulo."
            );
        }

        for (Categoria categoria : categorias) {

            if (categoria.getId().equals(id)
                    && !categoria.isEliminado()) {

                return categoria;
            }
        }

        throw new EntidadNoEncontradaException(
                "No existe una categoría con ID: " + id
        );
    }

    public void eliminarCategoria(Long id)
            throws EntidadNoEncontradaException {

        Categoria categoria = buscarPorId(id);

        categoria.setEliminado(true);
    }

    public void actualizarCategoria(
            Long id,
            String nombre,
            String descripcion)
            throws EntidadNoEncontradaException {

        Categoria categoria = buscarPorId(id);

        categoria.setNombre(nombre);
        categoria.setDescripcion(descripcion);
    }

    public int cantidadCategorias() {
        return listarCategorias().size();
    }
}