package integrador.entities;

import integrador.enums.Estado;
import integrador.enums.FormaPago;
import integrador.interfaces.Calculable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Pedido extends Base implements Calculable {
    private LocalDate fecha;
    private Estado estado;
    private Double total;
    private FormaPago formaPago;
    private List<DetallePedido> detalles;
    private Usuario usuario;
    
    // Contador estático auxiliar exclusivo para auto-generar IDs correlativos de DetallePedido internos
    private static long detalleIdSequence = 1;

    public Pedido() {
        super();
        this.fecha = LocalDate.now();
        this.detalles = new ArrayList<>();
        this.total = 0.0;
    }

    public Pedido(Long id, Estado estado, FormaPago formaPago) {
        super(id);
        this.fecha = LocalDate.now();
        this.estado = estado;
        this.formaPago = formaPago;
        this.detalles = new ArrayList<>();
        this.total = 0.0;
    }

    @Override
    public void calcularTotal() {
        double suma = 0.0;
        for (DetallePedido dp : detalles) {
            suma += dp.getSubtotal();
        }
        this.total = suma;
    }

    // Requisito: addDetallePedido(int cantidad, Producto producto)
    public void addDetallePedido(int cantidad, Producto producto) {
        DetallePedido nuevoDetalle = new DetallePedido(detalleIdSequence++, cantidad, producto, this);
        this.detalles.add(nuevoDetalle);
        calcularTotal();
    }

    // Requisito secundario del UML (con firma sobrecargada opcional si aplica, usamos la estricta)
    public void addDetallePedido(int cantidad, Double precioEspecifico, Producto producto) {
        // En este dominio, el precio se extrae del producto para mantener consistencia limpia
        addDetallePedido(cantidad, producto);
    }

    public DetallePedido findDetallePedidoByProducto(Producto producto) {
        if (producto == null) return null;
        for (DetallePedido dp : detalles) {
            if (dp.getProducto() != null && dp.getProducto().getId().equals(producto.getId())) {
                return dp;
            }
        }
        return null;
    }

    public void deleteDetallePedidoByProducto(Producto producto) {
        DetallePedido aEliminar = findDetallePedidoByProducto(producto);
        if (aEliminar != null) {
            this.detalles.remove(aEliminar);
            calcularTotal();
        }
    }

    // Getters y Setters
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }

    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }

    public FormaPago getFormaPago() { return formaPago; }
    public void setFormaPago(FormaPago formaPago) { this.formaPago = formaPago; }

    public List<DetallePedido> getDetalles() { return detalles; }
    public void setDetalles(List<DetallePedido> detalles) { 
        this.detalles = detalles; 
        calcularTotal();
    }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { 
        this.usuario = usuario; 
        if (usuario != null && !usuario.getPedidos().contains(this)) {
            usuario.agregarPedido(this);
        }
    }

    @Override
    public String toString() {
        return String.format("Pedido #[%d] | Fecha: [%s] | Estado: [%s] | FormaPago: [%s]", 
                getId(), fecha.toString(), estado.name(), formaPago.name());
    }
}