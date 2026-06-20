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

    public Categoria(Long id, String nombre, String descripcion) {
        super(id);
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.productos = new ArrayList<>();
    }

    public void agregarProducto(Producto producto) {
        if (producto != null) {
            this.productos.add(producto);
            // Mantenemos la consistencia bidireccional si no estaba asignada
            if (producto.getCategoria() != this) {
                producto.setCategoria(this);
            }
        }
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public List<Producto> getProductos() { return productos; }
    public void setProductos(List<Producto> productos) { this.productos = productos; }

    @Override
    public String toString() {
        return String.format("Categoria [ID: %d, Nombre: %s, Descripcion: %s]", getId(), nombre, descripcion);
    }
}
