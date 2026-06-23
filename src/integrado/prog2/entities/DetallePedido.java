/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package integrado.prog2.entities;

/**
 *
 * @author rocio
 */
public class DetallePedido extends Base {

    private int cantidad;
    private double subtotal;
    private Producto producto;

    public DetallePedido(int cantidad, double subtotal, Producto producto) {
        super();
        this.cantidad = cantidad;
        this.subtotal = subtotal;
        this.producto = producto;
    }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }
    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }

    @Override
    public String toString() {
        return String.format("  Detalle [ID: %d] Producto: %-20s | Cant: %d | Subtotal: $%.2f",
                getId(), producto.getNombre(), cantidad, subtotal);
    }
}