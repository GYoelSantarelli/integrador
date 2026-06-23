/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package integrado.prog2.service;

/**
 *
 * @author rocio
 */
import integrado.prog2.entities.Pedido;
import integrado.prog2.entities.Producto;
import integrado.prog2.entities.Usuario;
import integrado.prog2.enums.Estado;
import integrado.prog2.enums.FormaPago;
import integrado.prog2.exception.EntidadNoEncontradaException;

import java.util.ArrayList;
import java.util.List;

public class PedidoService {

    private final List<Pedido> pedidos = new ArrayList<>();

    public List<Pedido> listarActivos() {
        List<Pedido> activos = new ArrayList<>();
        for (Pedido p : pedidos) {
            if (!p.isEliminado()) activos.add(p);
        }
        return activos;
    }

    public List<Pedido> listarActivosPorUsuario(Long usuarioId) {
        List<Pedido> resultado = new ArrayList<>();
        for (Pedido p : pedidos) {
            if (!p.isEliminado() && p.getUsuario().getId().equals(usuarioId)) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    public Pedido buscarPorId(Long id) {
        for (Pedido p : pedidos) {
            if (p.getId().equals(id) && !p.isEliminado()) return p;
        }
        throw new EntidadNoEncontradaException("Pedido con ID " + id + " no encontrado o eliminado.");
    }

    public Pedido crear(Usuario usuario, FormaPago formaPago) {
        Pedido nuevo = new Pedido(usuario, formaPago);
        pedidos.add(nuevo);
        return nuevo;
    }

    public void agregarDetalle(Pedido pedido, int cantidad, Producto producto) {
        pedido.addDetallePedido(cantidad, producto.getPrecio(), producto);
    }

    public void actualizarEstadoYPago(Long id, Estado nuevoEstado, FormaPago nuevaFormaPago) {
        Pedido p = buscarPorId(id);
        if (nuevoEstado != null) p.setEstado(nuevoEstado);
        if (nuevaFormaPago != null) p.setFormaPago(nuevaFormaPago);
    }

    public void eliminar(Long id) {
        Pedido p = buscarPorId(id);
        p.setEliminado(true);
        p.getDetalles().forEach(d -> d.setEliminado(true));
    }

    public void cancelarPedidoEnMemoria(Pedido pedido) {
        pedidos.remove(pedido);
    }
}
