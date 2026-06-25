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

    public Pedido() {
        super();

        this.fecha = LocalDate.now();
        this.estado = Estado.PENDIENTE;
        this.formaPago = null;
        this.total = 0.0;
        this.detalles = new ArrayList<>();
    }

    public Pedido(FormaPago formaPago, Usuario usuario) {
        super();

        if (formaPago == null) {
            throw new IllegalArgumentException(
                    "La forma de pago no puede ser nula."
            );
        }

        this.fecha = LocalDate.now();
        this.estado = Estado.PENDIENTE;
        this.formaPago = formaPago;
        this.total = 0.0;
        this.detalles = new ArrayList<>();

        setUsuario(usuario);
    }

    @Override
    public void calcularTotal() {

        double suma = 0.0;

        for (DetallePedido detalle : detalles) {
            suma += detalle.getSubtotal();
        }

        this.total = suma;
    }

    public void addDetallePedido(
            int cantidad,
            Producto producto) {

        if (cantidad <= 0) {
            throw new IllegalArgumentException(
                    "La cantidad debe ser mayor a cero."
            );
        }

        if (producto == null) {
            throw new IllegalArgumentException(
                    "El producto no puede ser nulo."
            );
        }

        DetallePedido existente =
                findDetallePedidoByProducto(producto);

        if (existente != null) {

            existente.setCantidad(
                    existente.getCantidad() + cantidad
            );

        } else {

            DetallePedido nuevoDetalle =
                    new DetallePedido(
                            cantidad,
                            producto,
                            this
                    );

            detalles.add(nuevoDetalle);
        }

        calcularTotal();
    }

    public void addDetallePedido(
            int cantidad,
            Double precioEspecifico,
            Producto producto) {

        addDetallePedido(cantidad, producto);
    }

    public DetallePedido findDetallePedidoByProducto(
            Producto producto) {

        if (producto == null) {
            return null;
        }

        for (DetallePedido detalle : detalles) {

            if (detalle.getProducto() != null
                    && detalle.getProducto().getId()
                    .equals(producto.getId())) {

                return detalle;
            }
        }

        return null;
    }

    public void deleteDetallePedidoByProducto(
            Producto producto) {

        if (producto == null) {
            return;
        }

        DetallePedido detalle =
                findDetallePedidoByProducto(producto);

        if (detalle != null) {

            detalles.remove(detalle);

            calcularTotal();
        }
    }

    // GETTERS Y SETTERS

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {

        if (fecha == null) {
            throw new IllegalArgumentException(
                    "La fecha no puede ser nula."
            );
        }

        this.fecha = fecha;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {

        if (estado == null) {
            throw new IllegalArgumentException(
                    "El estado no puede ser nulo."
            );
        }

        this.estado = estado;
    }

    public Double getTotal() {
        return total;
    }

    public FormaPago getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(FormaPago formaPago) {

        if (formaPago == null) {
            throw new IllegalArgumentException(
                    "La forma de pago no puede ser nula."
            );
        }

        this.formaPago = formaPago;
    }

    public List<DetallePedido> getDetalles() {
        return detalles;
    }

    public void setDetalles(
            List<DetallePedido> detalles) {

        this.detalles =
                (detalles != null)
                        ? detalles
                        : new ArrayList<>();

        calcularTotal();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {

        this.usuario = usuario;

        if (usuario != null
                && !usuario.getPedidos().contains(this)) {

            usuario.agregarPedido(this);
        }
    }

    @Override
    public String toString() {

        String estadoTexto =
                (estado != null)
                        ? estado.name()
                        : "SIN_ESTADO";

        String formaPagoTexto =
                (formaPago != null)
                        ? formaPago.name()
                        : "SIN_FORMA_PAGO";

        String nombreUsuario =
                (usuario != null)
                        ? usuario.getNombre()
                        : "SIN_USUARIO";

        return String.format(
                "Pedido #%d | Usuario: %s | Fecha: %s | Estado: %s | Forma de Pago: %s | Total: $%.2f",
                getId(),
                nombreUsuario,
                fecha,
                estadoTexto,
                formaPagoTexto,
                total
        );
    }
}