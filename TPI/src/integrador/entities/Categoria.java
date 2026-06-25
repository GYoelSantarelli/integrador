package integrador.entities;

import java.util.ArrayList;
import java.util.List;

public class Categoria extends Base {

    private String nombre;
    private String descripcion;
    private List<Producto> productos;

    public Categoria() {
        super();
        this.productos = new ArrayList<>();
    }

    public Categoria(
            String nombre,
            String descripcion) {

        super();

        setNombre(nombre);
        setDescripcion(descripcion);

        this.productos = new ArrayList<>();
    }

    public void agregarProducto(Producto producto) {

        if (producto == null) {
            return;
        }

        if (!productos.contains(producto)) {

            productos.add(producto);

            if (producto.getCategoria() != this) {
                producto.setCategoria(this);
            }
        }
    }

    public void eliminarProducto(Producto producto) {

        if (producto == null) {
            return;
        }

        productos.remove(producto);
    }

    // GETTERS Y SETTERS

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {

        if (nombre == null ||
                nombre.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "El nombre de la categoría es obligatorio."
            );
        }

        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {

        this.descripcion =
                (descripcion != null)
                        ? descripcion
                        : "";
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {

        this.productos =
                (productos != null)
                        ? productos
                        : new ArrayList<>();
    }

    @Override
    public String toString() {

        return String.format(
                "Categoria #%d | %s | Productos asociados: %d",
                getId(),
                nombre,
                productos.size()
        );
    }
}