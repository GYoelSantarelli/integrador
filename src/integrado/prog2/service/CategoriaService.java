/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package integrado.prog2.service;

/**
 *
 * @author rocio
 */
import integrado.prog2.entities.Categoria;
import integrado.prog2.exception.EntidadNoEncontradaException;
import integrado.prog2.exception.ValidacionException;

import java.util.ArrayList;
import java.util.List;

public class CategoriaService {

    private final List<Categoria> categorias = new ArrayList<>();

    public List<Categoria> listarActivas() {
        List<Categoria> activas = new ArrayList<>();
        for (Categoria c : categorias) {
            if (!c.isEliminado()) activas.add(c);
        }
        return activas;
    }

    public Categoria buscarPorId(Long id) {
        for (Categoria c : categorias) {
            if (c.getId().equals(id) && !c.isEliminado()) return c;
        }
        throw new EntidadNoEncontradaException("Categoría con ID " + id + " no encontrada o eliminada.");
    }

    public Categoria crear(String nombre, String descripcion) {
        if (nombre == null || nombre.isBlank())
            throw new ValidacionException("El nombre no puede estar vacío.");
        if (descripcion == null || descripcion.isBlank())
            throw new ValidacionException("La descripción no puede estar vacía.");
        for (Categoria c : categorias) {
            if (!c.isEliminado() && c.getNombre().equalsIgnoreCase(nombre)) {
                throw new ValidacionException("Ya existe una categoría con el nombre '" + nombre + "'.");
            }
        }
        Categoria nueva = new Categoria(nombre, descripcion);
        categorias.add(nueva);
        return nueva;
    }

    public void editar(Long id, String nuevoNombre, String nuevaDescripcion) {
        Categoria c = buscarPorId(id);
        if (nuevoNombre != null && !nuevoNombre.isBlank()) {
            for (Categoria otro : categorias) {
                if (!otro.isEliminado() && !otro.getId().equals(id)
                        && otro.getNombre().equalsIgnoreCase(nuevoNombre)) {
                    throw new ValidacionException("Ya existe una categoría con ese nombre.");
                }
            }
            c.setNombre(nuevoNombre);
        }
        if (nuevaDescripcion != null && !nuevaDescripcion.isBlank()) {
            c.setDescripcion(nuevaDescripcion);
        }
    }

    public void eliminar(Long id) {
        Categoria c = buscarPorId(id);
        c.setEliminado(true);
    }

    public boolean tieneProductosActivos(Long id, ProductoService productoService) {
        return productoService.listarActivos().stream()
                .anyMatch(p -> p.getCategoria() != null && p.getCategoria().getId().equals(id));
    }
}
