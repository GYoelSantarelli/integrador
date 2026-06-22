package integrador.service;

import integrador.entities.Pedido;
import integrador.entities.Producto;
import integrador.exception.EntidadNoEncontradaException;
import integrador.exception.StockInsuficienteException;

import java.util.ArrayList;
import java.util.List;

public class PedidoService {

    private List<Pedido> pedidos;

    public PedidoService() {
        this.pedidos = new ArrayList<>();
    }

    public void crearPedido(Pedido pedido) {

        if (pedido == null) {
            throw new IllegalArgumentException(
                    "El pedido no puede ser nulo."
            );
        }

        pedidos.add(pedido);
    }

    public List<Pedido> listarPedidos() {

        List<Pedido> activos = new ArrayList<>();

        for (Pedido pedido : pedidos) {

            if (!pedido.isEliminado()) {
                activos.add(pedido);
            }
        }

        return activos;
    }

    public Pedido buscarPorId(Long id)
            throws EntidadNoEncontradaException {

        if (id == null) {
            throw new IllegalArgumentException(
                    "El ID no puede ser nulo."
            );
        }

        for (Pedido pedido : pedidos) {

            if (pedido.getId().equals(id)
                    && !pedido.isEliminado()) {

                return pedido;
            }
        }

        throw new EntidadNoEncontradaException(
                "No existe un pedido con ID: " + id
        );
    }

    public void eliminarPedido(Long id)
            throws EntidadNoEncontradaException {

        Pedido pedido = buscarPorId(id);

        pedido.setEliminado(true);
    }

    public void agregarProducto(
            Long idPedido,
            Producto producto,
            int cantidad)
            throws EntidadNoEncontradaException,
                   StockInsuficienteException {

        if (producto == null) {

            throw new IllegalArgumentException(
                    "El producto no puede ser nulo."
            );
        }

        if (cantidad <= 0) {

            throw new IllegalArgumentException(
                    "La cantidad debe ser mayor a cero."
            );
        }

        Pedido pedido = buscarPorId(idPedido);

        if (cantidad > producto.getStock()) {

            throw new StockInsuficienteException(
                    "Stock insuficiente para el producto "
                            + producto.getNombre()
            );
        }

        pedido.addDetallePedido(cantidad, producto);

        producto.setStock(
                producto.getStock() - cantidad
        );
    }

    public int cantidadPedidos() {
        return listarPedidos().size();
    }
}