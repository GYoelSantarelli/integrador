package integrador;

import integrador.entities.*;
import integrador.enums.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        
        // ---------------------------------------------------------
        // 1. INSTANCIACIÓN DE 3 CATEGORÍAS (Nombres y desc distintas)
        // ---------------------------------------------------------
        Categoria cat1 = new Categoria(1L, "Hamburguesas", "Burgers artesanales con ingredientes frescos");
        Categoria cat2 = new Categoria(2L, "Bebidas", "Gaseosas, jugos naturales y cervezas frías");
        Categoria cat3 = new Categoria(3L, "Postres", "Dulces tentaciones para finalizar tu comida");

        // ---------------------------------------------------------
        // 2. INSTANCIACIÓN DE 6 PRODUCTOS (2 por cada categoría)
        // ---------------------------------------------------------
        Producto prod1 = new Producto(101L, "Cheeseburger Classic", 1200.0, "Carne de 150g y cheddar", 50, "burger1.jpg", true);
        Producto prod2 = new Producto(102L, "BBQ Bacon Burger", 1500.0, "Salsa barbacoa y panceta", 40, "burger2.jpg", true);
        cat1.agregarProducto(prod1);
        cat1.agregarProducto(prod2);

        Producto prod3 = new Producto(201L, "Coca-Cola Original 500ml", 350.0, "Refresco bien frío", 100, "coca.jpg", true);
        Producto prod4 = new Producto(202L, "Cerveza IPArtisan", 600.0, "Cerveza tirada artesanal", 60, "ipa.jpg", true);
        cat2.agregarProducto(prod3);
        cat2.agregarProducto(prod4);

        Producto prod5 = new Producto(301L, "Volcán de Chocolate", 750.0, "Corazón de chocolate líquido", 25, "volcan.jpg", true);
        Producto prod6 = new Producto(302L, "Helado de Dulce de Leche", 500.0, "Cremoso helado artesanal", 30, "helado.jpg", true);
        cat3.agregarProducto(prod5);
        cat3.agregarProducto(prod6);

        // ---------------------------------------------------------
        // 3. INSTANCIACIÓN DE 2 USUARIOS (Roles diferentes)
        // ---------------------------------------------------------
        Usuario admin = new Usuario(1L, "Carlos", "Gómez", "carlos.admin@foodstore.com", "11223344", "passAdmin123", Rol.ADMIN);
        Usuario cliente = new Usuario(2L, "Ana", "Martínez", "ana.user@gmail.com", "55667788", "anaPass2026", Rol.USUARIO);

        List<Usuario> listaUsuarios = new ArrayList<>();
        listaUsuarios.add(admin);
        listaUsuarios.add(cliente);

        // ---------------------------------------------------------
        // 4. INSTANCIACIÓN DE 4 PEDIDOS (2 por cada usuario) Y 12 DETALLES
        //    (3 detalles por pedido utilizando obligatoriamente addDetallePedido)
        // ---------------------------------------------------------
        
        // --- PEDIDOS DE CARLOS (ADMIN) ---
        Pedido pedCarlos1 = new Pedido(1001L, Estado.TERMINADO, FormaPago.TARJETA);
        pedCarlos1.addDetallePedido(2, prod1); // 2x Cheeseburger
        pedCarlos1.addDetallePedido(2, prod3); // 2x Coca-Cola
        pedCarlos1.addDetallePedido(1, prod5); // 1x Volcán Chocolate
        admin.agregarPedido(pedCarlos1); // Relación bidireccional

        Pedido pedCarlos2 = new Pedido(1002L, Estado.CONFIRMADO, FormaPago.TRANSFERENCIA);
        pedCarlos2.addDetallePedido(1, prod2); // 1x BBQ Burger
        pedCarlos2.addDetallePedido(1, prod4); // 1x Cerveza IPA
        pedCarlos2.addDetallePedido(2, prod6); // 2x Helado DDL
        admin.agregarPedido(pedCarlos2);

        // --- PEDIDOS DE ANA (USUARIO) ---
        Pedido pedAna1 = new Pedido(1003L, Estado.CONFIRMADO, FormaPago.EFECTIVO);
        pedAna1.addDetallePedido(3, prod1); // 3x Cheeseburger
        pedAna1.addDetallePedido(3, prod3); // 3x Coca-Cola
        pedAna1.addDetallePedido(2, prod5); // 2x Volcán Chocolate
        cliente.agregarPedido(pedAna1);

        Pedido pedAna2 = new Pedido(1004L, Estado.PENDIENTE, FormaPago.TARJETA);
        pedAna2.addDetallePedido(1, prod2); // 1x BBQ Burger
        pedAna2.addDetallePedido(2, prod4); // 2x Cerveza IPA
        pedAna2.addDetallePedido(1, prod6); // 1x Helado DDL
        cliente.agregarPedido(pedAna2);


        // ---------------------------------------------------------
        // 5. SALIDA POR CONSOLA REQUERIDA
        // ---------------------------------------------------------
        for (Usuario u : listaUsuarios) {
            System.out.println("=============================================================================");
            System.out.println(u.toString());
            System.out.println("=============================================================================");
            
            for (Pedido p : u.getPedidos()) {
                System.out.println("> " + p.toString());
                System.out.println("-----------------------------------------------------------------------------");
                
                for (DetallePedido dp : p.getDetalles()) {
                    System.out.println("  - DetallePedido #[" + dp.getId() + "]: " + dp.toString());
                }
                
                System.out.printf("  TOTAL DEL PEDIDO: $%.2f\n", p.getTotal());
                System.out.println("-----------------------------------------------------------------------------");
            }
            
            System.out.printf("TOTAL ACUMULADO del usuario: $%.2f\n", u.obtenerTotalAcumulado());
            System.out.println("=============================================================================\n");
        }
    }
}