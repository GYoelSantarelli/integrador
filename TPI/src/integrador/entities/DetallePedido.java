package integrador.entities;

public class DetallePedido extends Base {
    private int cantidad;
    private Double subtotal;
    private Producto producto;
    private Pedido pedido; 

    public DetallePedido() {
        super();
    }

    public DetallePedido(Long id, int cantidad, Producto producto, Pedido pedido) {
        super(id);
        this.producto = producto;
        this.pedido = pedido;
        setCantidad(cantidad); 
    }

    public Double calcularSubtotal() {
        if (producto != null) {
            this.subtotal = this.cantidad * producto.getPrecio();
        } else {
            this.subtotal = 0.0;
        }
        return this.subtotal;
    }

    // Getters y Setters
    public int getCantidad() { return cantidad; }
    
    public void setCantidad(int cantidad) { 
        this.cantidad = cantidad; 
        calcularSubtotal();
        // Si el detalle ya pertenece a un pedido, forzar recálculo del total general
        if (this.pedido != null) {
            this.pedido.calcularTotal();
        }
    }

    public Double getSubtotal() { return subtotal; }
    // El subtotal no debería modificarse externamente sin lógica, pero cumplimos la estructura estándar
    public void setSubtotal(Double subtotal) { this.subtotal = subtotal; }

    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { 
        this.producto = producto; 
        calcularSubtotal();
        if (this.pedido != null) {
            this.pedido.calcularTotal();
        }
    }

    public Pedido getPedido() { return pedido; }
    public void setPedido(Pedido pedido) { this.pedido = pedido; }

    @Override
    public String toString() {
        return String.format("%s x %d => Subtotal: $%.2f", producto.getNombre(), cantidad, subtotal);
    }
}