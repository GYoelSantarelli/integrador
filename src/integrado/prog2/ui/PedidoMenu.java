/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package integrado.prog2.ui;

/**
 *
 * @author rocio
 */
import integrado.prog2.entities.Pedido;
import integrado.prog2.entities.Producto;
import integrado.prog2.entities.Usuario;
import integrado.prog2.enums.Estado;
import integrado.prog2.enums.FormaPago;
import integrado.prog2.service.PedidoService;
import integrado.prog2.service.ProductoService;
import integrado.prog2.service.UsuarioService;

import java.util.List;
import java.util.Scanner;

public class PedidoMenu {

    private final PedidoService pedidoService;
    private final UsuarioService usuarioService;
    private final ProductoService productoService;
    private final Scanner scanner;

    public PedidoMenu(PedidoService pedidoService, UsuarioService usuarioService,
                      ProductoService productoService, Scanner scanner) {
        this.pedidoService = pedidoService;
        this.usuarioService = usuarioService;
        this.productoService = productoService;
        this.scanner = scanner;
    }

    public void mostrar() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n___ GESTION DE PEDIDOS ___");
            System.out.println("1. Listar pedidos");
            System.out.println("2. Crear pedido");
            System.out.println("3. Actualizar estado / forma de pago");
            System.out.println("4. Eliminar pedido");
            System.out.println("0. Volver");
            System.out.print("Seleccione: ");
            int opcion = leerEntero();
            switch (opcion) {
                case 1 -> listar();
                case 2 -> crear();
                case 3 -> actualizar();
                case 4 -> eliminar();
                case 0 -> volver = true;
                default -> System.out.println(" Opcion invalida.");
            }
        }
    }

    private void listar() {
        System.out.println("\n1. Todos  2. Filtrar por usuario");
        System.out.print("Opción: ");
        List<Pedido> lista;
        if (leerEntero() == 2) {
            usuarioService.listarActivos().forEach(System.out::println);
            System.out.print("ID de usuario: ");
            Long uid = leerLong();
            if (uid == null) return;
            lista = pedidoService.listarActivosPorUsuario(uid);
        } else {
            lista = pedidoService.listarActivos();
        }
        System.out.println("\n___ PEDIDOS ___");
        if (lista.isEmpty()) System.out.println("No hay pedidos.");
        else {
            for (Pedido p : lista) {
                System.out.println(p);
                p.getDetalles().stream().filter(d -> !d.isEliminado()).forEach(System.out::println);
            }
        }
    }

    private void crear() {
        System.out.println("\n___ CREAR PEDIDO ___");
        usuarioService.listarActivos().forEach(System.out::println);
        System.out.print("ID de usuario: ");
        Long uid = leerLong();
        if (uid == null) return;

        Pedido pedido;
        try {
            Usuario usuario = usuarioService.buscarPorId(uid);
            FormaPago formaPago = seleccionarFormaPago();
            pedido = pedidoService.crear(usuario, formaPago);
        } catch (Exception e) {
            System.out.println(" Error al crear pedido: " + e.getMessage());
            return;
        }

        boolean agregarMas = true;
        while (agregarMas) {
            productoService.listarActivos().forEach(System.out::println);
            System.out.print("ID de producto (0 para terminar): ");
            Long pid = leerLong();
            if (pid == null || pid == 0) break;
            System.out.print("Cantidad: ");
            Integer cantidad = leerEnteroPositivo();
            if (cantidad == null) continue;
            try {
                Producto producto = productoService.buscarPorId(pid);
                pedidoService.agregarDetalle(pedido, cantidad, producto);
                System.out.printf(" Detalle agregado. Total del pedido: $%.2f%n", pedido.getTotal());
            } catch (Exception e) {
                System.out.println(" Error: " + e.getMessage());
                System.out.print("¿Cancelar el pedido completo? (S/N): ");
                if (scanner.nextLine().trim().equalsIgnoreCase("S")) {
                    pedidoService.cancelarPedidoEnMemoria(pedido);
                    System.out.println("Pedido cancelado.");
                    return;
                }
            }
            System.out.print("¿Agregar otro producto? (S/N): ");
            agregarMas = scanner.nextLine().trim().equalsIgnoreCase("S");
        }

        if (pedido.getDetalles().isEmpty()) {
            pedidoService.cancelarPedidoEnMemoria(pedido);
            System.out.println(" Pedido sin detalles, cancelado.");
        } else {
            System.out.printf(" Pedido #%d creado. Total: $%.2f%n", pedido.getId(), pedido.getTotal());
        }
    }

    private void actualizar() {
        pedidoService.listarActivos().forEach(System.out::println);
        System.out.println("\n___ ACTUALIZAR PEDIDO ___");
        System.out.print("ID del pedido: ");
        Long id = leerLong();
        if (id == null) return;
        try {
            pedidoService.buscarPorId(id); // valida existencia
            Estado[] estados = Estado.values();
            System.out.println("Estado (0 para no cambiar):");
            for (int i = 0; i < estados.length; i++)
                System.out.println("  " + (i + 1) + ". " + estados[i]);
            System.out.print("Seleccione: ");
            int eIdx = leerEntero();
            Estado nuevoEstado = (eIdx >= 1 && eIdx <= estados.length) ? estados[eIdx - 1] : null;

            FormaPago[] formas = FormaPago.values();
            System.out.println("Forma de pago (0 para no cambiar):");
            for (int i = 0; i < formas.length; i++)
                System.out.println("  " + (i + 1) + ". " + formas[i]);
            System.out.print("Seleccione: ");
            int fIdx = leerEntero();
            FormaPago nuevaFormaPago = (fIdx >= 1 && fIdx <= formas.length) ? formas[fIdx - 1] : null;

            pedidoService.actualizarEstadoYPago(id, nuevoEstado, nuevaFormaPago);
            System.out.println(" Pedido actualizado.");
        } catch (Exception e) {
            System.out.println(" Error: " + e.getMessage());
        }
    }

    private void eliminar() {
        pedidoService.listarActivos().forEach(System.out::println);
        System.out.println("ELIMINAR PEDIDO");
        System.out.println("_______________");
        System.out.print("ID del pedido: ");
        Long id = leerLong();
        if (id == null) return;
        try {
            Pedido p = pedidoService.buscarPorId(id);
            System.out.print("¿Confirma eliminar pedido #" + p.getId() + "? (S/N): ");
            if (scanner.nextLine().trim().equalsIgnoreCase("S")) {
                pedidoService.eliminar(id);
                System.out.println(" Pedido eliminado (baja logica).");
            } else {
                System.out.println("Operacion cancelada.");
            }
        } catch (Exception e) {
            System.out.println(" Error: " + e.getMessage());
        }
    }

    private FormaPago seleccionarFormaPago() {
        FormaPago[] formas = FormaPago.values();
        System.out.println("Forma de pago:");
        for (int i = 0; i < formas.length; i++)
            System.out.println("  " + (i + 1) + ". " + formas[i]);
        System.out.print("Seleccione: ");
        int idx = leerEntero();
        return (idx >= 1 && idx <= formas.length) ? formas[idx - 1] : FormaPago.EFECTIVO;
    }

    private int leerEntero() {
        try { return Integer.parseInt(scanner.nextLine().trim()); }
        catch (NumberFormatException e) { return -1; }
    }

    private Integer leerEnteroPositivo() {
        try {
            int v = Integer.parseInt(scanner.nextLine().trim());
            if (v <= 0) { System.out.println(" Debe ser mayor a 0."); return null; }
            return v;
        } catch (NumberFormatException e) { System.out.println(" Numero invalido."); return null; }
    }

    private Long leerLong() {
        try { return Long.parseLong(scanner.nextLine().trim()); }
        catch (NumberFormatException e) { System.out.println(" ID invalido."); return null; }
    }
}