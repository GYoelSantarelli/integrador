package integrador.entities;

public class DetallePedido extends Base {

    private int cantidad;
    private Double subtotal;
    private Producto producto;
    private Pedido pedido;

    public DetallePedido() {
        super();
        this.subtotal = 0.0;
    }

    public DetallePedido(Long id,
                         int cantidad,
                         Producto producto,
                         Pedido pedido) {

        super(id);

        this.pedido = pedido;

        setProducto(producto);
        setCantidad(cantidad);
    }

    public Double calcularSubtotal() {

        if (producto != null) {
            this.subtotal = cantidad * producto.getPrecio();
        } else {
            this.subtotal = 0.0;
        }

        return subtotal;
    }
    
    //Getters y setters

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {

        if (cantidad <= 0) {
            throw new IllegalArgumentException(
                    "La cantidad debe ser mayor a cero."
            );
        }

        this.cantidad = cantidad;

        calcularSubtotal();

        if (pedido != null) {
            pedido.calcularTotal();
        }
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {

        if (producto == null) {
            throw new IllegalArgumentException(
                    "El producto no puede ser nulo."
            );
        }

        this.producto = producto;

        calcularSubtotal();

        if (pedido != null) {
            pedido.calcularTotal();
        }
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;

        if (pedido != null) {
            pedido.calcularTotal();
        }
    }

    @Override
    public String toString() {

        String nombreProducto =
                (producto != null)
                        ? producto.getNombre()
                        : "Sin producto";

        return String.format(
                "%s x %d => Subtotal: $%.2f",
                nombreProducto,
                cantidad,
                subtotal
        );
    }
}