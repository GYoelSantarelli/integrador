package integrador.service;

import integrador.entities.Producto;
import integrador.exception.EntidadNoEncontradaException;

import java.util.ArrayList;
import java.util.List;

public class ProductoService {

    private List<Producto> productos;

    public ProductoService() {
        this.productos = new ArrayList<>();
    }

    public void crearProducto(Producto producto) {

        if (producto == null) {
            throw new IllegalArgumentException(
                    "El producto no puede ser nulo."
            );
        }

        productos.add(producto);
    }

    public List<Producto> listarProductos() {

        List<Producto> activos = new ArrayList<>();

        for (Producto producto : productos) {

            if (!producto.isEliminado()) {
                activos.add(producto);
            }
        }

        return activos;
    }

    public Producto buscarPorId(Long id)
            throws EntidadNoEncontradaException {

        if (id == null) {
            throw new IllegalArgumentException(
                    "El ID no puede ser nulo."
            );
        }

        for (Producto producto : productos) {

            if (producto.getId().equals(id)
                    && !producto.isEliminado()) {

                return producto;
            }
        }

        throw new EntidadNoEncontradaException(
                "No existe un producto con ID: " + id
        );
    }

    public void eliminarProducto(Long id)
            throws EntidadNoEncontradaException {

        Producto producto = buscarPorId(id);

        producto.setEliminado(true);
    }

    public void actualizarProducto(
            Long id,
            String nombre,
            Double precio,
            String descripcion,
            int stock,
            boolean disponible)
            throws EntidadNoEncontradaException {

        Producto producto = buscarPorId(id);

        producto.setNombre(nombre);
        producto.setPrecio(precio);
        producto.setDescripcion(descripcion);
        producto.setStock(stock);
        producto.setDisponible(disponible);
    }

    public List<Producto> buscarPorNombre(String nombre) {

        List<Producto> resultado = new ArrayList<>();

        if (nombre == null || nombre.trim().isEmpty()) {
            return resultado;
        }

        for (Producto producto : productos) {

            if (!producto.isEliminado()
                    && producto.getNombre() != null
                    && producto.getNombre()
                    .toLowerCase()
                    .contains(nombre.toLowerCase())) {

                resultado.add(producto);
            }
        }

        return resultado;
    }

    public int cantidadProductos() {
        return listarProductos().size();
    }
}