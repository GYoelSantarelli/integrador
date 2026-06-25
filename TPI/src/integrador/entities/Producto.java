package integrador.entities;

public class Producto extends Base {

    private String nombre;
    private Double precio;
    private String descripcion;
    private int stock;
    private String imagen;
    private boolean disponible;
    private Categoria categoria;

    public Producto() {
        super();
    }

    public Producto(
            String nombre,
            Double precio,
            String descripcion,
            int stock,
            String imagen,
            boolean disponible) {

        super();

        setNombre(nombre);
        setPrecio(precio);
        setDescripcion(descripcion);
        setStock(stock);
        setImagen(imagen);
        setDisponible(disponible);
    }

    // GETTERS Y SETTERS

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {

        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El nombre no puede estar vacío."
            );
        }

        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {

        if (precio == null || precio < 0) {
            throw new IllegalArgumentException(
                    "El precio no puede ser nulo ni negativo."
            );
        }

        this.precio = precio;
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

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {

        if (stock < 0) {
            throw new IllegalArgumentException(
                    "El stock no puede ser negativo."
            );
        }

        this.stock = stock;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {

        this.categoria = categoria;

        if (categoria != null &&
                !categoria.getProductos().contains(this)) {

            categoria.agregarProducto(this);
        }
    }

    @Override
    public String toString() {

        return String.format(
                "Producto #%d | %s | $%.2f | Stock: %d | Disponible: %s | Categoría: %s",
                getId(),
                nombre,
                precio,
                stock,
                disponible ? "SI" : "NO",
                categoria != null
                        ? categoria.getNombre()
                        : "Sin categoría"
        );
    }
}