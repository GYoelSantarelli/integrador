/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package integrado.prog2.entities;

/**
 *
 * @author rocio
 */
import integrado.prog2.enums.Estado;
import integrado.prog2.enums.FormaPago;
import integrado.prog2.exception.EntidadNoEncontradaException;
import integrado.prog2.exception.StockInvalidoException;
import integrado.prog2.exception.ValidacionException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Pedido extends Base implements Calculable {

    private LocalDate fecha;
    private Estado estado;
    private double total;
    private FormaPago formaPago;
    private Usuario usuario;
    private List<DetallePedido> detalles;

    public Pedido(Usuario usuario, FormaPago formaPago) {
        super();
        if (usuario == null) throw new ValidacionException("El pedido debe tener un usuario asociado.");
        this.usuario = usuario;
        this.fecha = LocalDate.now();
        this.estado = Estado.PENDIENTE;
        this.formaPago = formaPago;
        this.total = 0.0;
        this.detalles = new ArrayList<>();
    }

    public void addDetallePedido(int cantidad, Double precioUnitario, Producto producto) {
        if (cantidad <= 0) throw new ValidacionException("La cantidad debe ser mayor a 0.");
        if (producto == null) throw new ValidacionException("El producto no puede ser nulo.");
        if (producto.isEliminado()) throw new ValidacionException("El producto fue eliminado.");
        if (!producto.isDisponible()) throw new ValidacionException("El producto no está disponible.");
        if (producto.getStock() < cantidad) {
            throw new StockInvalidoException(
                "Stock insuficiente. Stock actual: " + producto.getStock() + ", solicitado: " + cantidad);
        }
        DetallePedido existente = findeDetallePedidoByProducto(producto);
        if (existente != null) {
            int nuevaCantidad = existente.getCantidad() + cantidad;
            if (producto.getStock() < nuevaCantidad) {
                throw new StockInvalidoException(
                    "Stock insuficiente para la cantidad total. Stock: " + producto.getStock());
            }
            existente.setCantidad(nuevaCantidad);
            existente.setSubtotal(nuevaCantidad * precioUnitario);
        } else {
            double subtotal = cantidad * precioUnitario;
            DetallePedido detalle = new DetallePedido(cantidad, subtotal, producto);
            detalles.add(detalle);
        }
        calcularTotal();
    }

    public DetallePedido findeDetallePedidoByProducto(Producto producto) {
        for (DetallePedido d : detalles) {
            if (!d.isEliminado() && d.getProducto().equals(producto)) return d;
        }
        return null;
    }

    public void deleteDetallePedidoByProducto(Producto producto) {
        DetallePedido detalle = findeDetallePedidoByProducto(producto);
        if (detalle == null) {
            throw new EntidadNoEncontradaException("No se encontró detalle para ese producto.");
        }
        detalle.setEliminado(true);
        calcularTotal();
    }

    @Override
    public void calcularTotal() {
        this.total = detalles.stream()
                .filter(d -> !d.isEliminado())
                .mapToDouble(DetallePedido::getSubtotal)
                .sum();
    }

    public LocalDate getFecha() { return fecha; }
    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }
    public double getTotal() { return total; }
    public FormaPago getFormaPago() { return formaPago; }
    public void setFormaPago(FormaPago formaPago) { this.formaPago = formaPago; }
    public Usuario getUsuario() { return usuario; }
    public List<DetallePedido> getDetalles() { return detalles; }

    @Override
    public String toString() {
        return String.format("[ID: %d] Usuario: %-20s | Estado: %-12s | Pago: %-15s | Total: $%-10.2f | Fecha: %s",
                getId(),
                usuario.getNombre() + " " + usuario.getApellido(),
                estado, formaPago, total, fecha);
    }
}
