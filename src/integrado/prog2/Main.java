/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package integrado.prog2;

/**
 *
 * @author rocio
 */
import integrado.prog2.service.CategoriaService;
import integrado.prog2.service.PedidoService;
import integrado.prog2.service.ProductoService;
import integrado.prog2.service.UsuarioService;
import integrado.prog2.ui.CategoriaMenu;
import integrado.prog2.ui.PedidoMenu;
import integrado.prog2.ui.ProductoMenu;
import integrado.prog2.ui.UsuarioMenu;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        CategoriaService categoriaService = new CategoriaService();
        ProductoService  productoService  = new ProductoService();
        UsuarioService   usuarioService   = new UsuarioService();
        PedidoService    pedidoService    = new PedidoService();

        CategoriaMenu categoriaMenu = new CategoriaMenu(categoriaService, productoService, scanner);
        ProductoMenu  productoMenu  = new ProductoMenu(productoService, categoriaService, scanner);
        UsuarioMenu   usuarioMenu   = new UsuarioMenu(usuarioService, scanner);
        PedidoMenu    pedidoMenu    = new PedidoMenu(pedidoService, usuarioService, productoService, scanner);

        boolean salir = false;
        while (!salir) {
            System.out.println("____________________________________");
            System.out.println("|  SISTEMA DE PEDIDOS (FOOD STORE) |");
            System.out.println("|______________________________|");
            System.out.println("|  1. Categorias                   |");
            System.out.println("|  2. Productos                    |");
            System.out.println("|  3. Usuarios                     |");
            System.out.println("|  4. Pedidos                      |");
            System.out.println("|  0. Salir                        |");
            System.out.println("|______________________________|");
            System.out.print("Seleccione: ");

            switch (scanner.nextLine().trim()) {
                case "1" -> categoriaMenu.mostrar();
                case "2" -> productoMenu.mostrar();
                case "3" -> usuarioMenu.mostrar();
                case "4" -> pedidoMenu.mostrar();
                case "0" -> { System.out.println("Hasta luego!"); salir = true; }
                default  -> System.out.println(" Opción invalida.");
            }
        }
        scanner.close();
    }
}
