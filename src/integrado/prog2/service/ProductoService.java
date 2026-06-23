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
import integrado.prog2.entities.Producto;
import integrado.prog2.exception.EntidadNoEncontradaException;
import integrado.prog2.exception.ValidacionException;

import java.util.ArrayList;
import java.util.List;

public class ProductoService {

    private final List<Producto> productos = new ArrayList<>();

    public List<Producto> listarActivos() {
        List<Producto> activos = new ArrayList<>();
        for (Producto p : productos) {
            if (!p.isEliminado()) activos.add(p);
        }
        return activos;
    }

    public List<Producto> listarActivosPorCategoria(Long categoriaId) {
        List<Producto> resultado = new ArrayList<>();
        for (Producto p : productos) {
            if (!p.isEliminado() && p.getCategoria() != null
                    && p.getCategoria().getId().equals(categoriaId)) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    public Producto buscarPorId(Long id) {
        for (Producto p : productos) {
            if (p.getId().equals(id) && !p.isEliminado()) return p;
        }
        throw new EntidadNoEncontradaException("Producto con ID " + id + " no encontrado o eliminado.");
    }

    public Producto crear(String nombre, double precio, String descripcion, int stock,
                          String imagen, boolean disponible, Categoria categoria) {
        if (nombre == null || nombre.isBlank())
            throw new ValidacionException("El nombre no puede estar vacío.");
        if (categoria == null || categoria.isEliminado())
            throw new ValidacionException("La categoría no existe o fue eliminada.");
        Producto nuevo = new Producto(nombre, precio, descripcion, stock, imagen, disponible, categoria);
        productos.add(nuevo);
        return nuevo;
    }

    public void editar(Long id, String nombre, Double precio, String descripcion,
                       Integer stock, String imagen, Boolean disponible, Categoria categoria) {
        Producto p = buscarPorId(id);
        if (nombre != null && !nombre.isBlank()) p.setNombre(nombre);
        if (precio != null) p.setPrecio(precio);
        if (descripcion != null && !descripcion.isBlank()) p.setDescripcion(descripcion);
        if (stock != null) p.setStock(stock);
        if (imagen != null && !imagen.isBlank()) p.setImagen(imagen);
        if (disponible != null) p.setDisponible(disponible);
        if (categoria != null && !categoria.isEliminado()) p.setCategoria(categoria);
    }

    public void eliminar(Long id) {
        Producto p = buscarPorId(id);
        p.setEliminado(true);
    }
}
