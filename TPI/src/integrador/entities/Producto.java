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

    public Producto(Long id,
                    String nombre,
                    Double precio,
                    String descripcion,
                    int stock,
                    String imagen,
                    boolean disponible) {

        super(id);
        this.nombre = nombre;
        setPrecio(precio);
        this.descripcion = descripcion;
        setStock(stock);
        this.imagen = imagen;
        this.disponible = disponible;
    }

    // Getters y Setters

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
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
        this.descripcion = descripcion;
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

        // Mantiene sincronizada la relación bidireccional
        if (categoria != null &&
                !categoria.getProductos().contains(this)) {

            categoria.agregarProducto(this);
        }
    }

    @Override

    public String toString() {

    return String.format(
            "Producto [ID=%d, Nombre=%s, Precio=$%.2f, Stock=%d, Disponible=%s, Categoria=%s]",
            getId(),
            nombre,
            precio,
            stock,
            disponible ? "SI" : "NO",
            (categoria != null
                    ? categoria.getNombre()
                    : "Sin categoría")
    );
}
}