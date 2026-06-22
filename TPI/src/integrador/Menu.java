package integrador;

import java.util.Scanner;

public class Menu {

    private Scanner scanner;

    public Menu() {
        scanner = new Scanner(System.in);
    }

    public int mostrarMenuPrincipal() {

        System.out.println("\n=========================");
        System.out.println(" SISTEMA DE PEDIDOS ");
        System.out.println("=========================");
        System.out.println("1 - Gestionar Categorías");
        System.out.println("2 - Gestionar Productos");
        System.out.println("3 - Gestionar Usuarios");
        System.out.println("4 - Gestionar Pedidos");
        System.out.println("0 - Salir");
        System.out.print("Opción: ");

        return scanner.nextInt();
    }
    public int mostrarMenuCategorias() {

    System.out.println("\n=========================");
    System.out.println(" GESTION DE CATEGORIAS ");
    System.out.println("=========================");
    System.out.println("1 - Crear categoria");
    System.out.println("2 - Listar categorias");
    System.out.println("3 - Buscar categoria");
    System.out.println("4 - Eliminar categoria");
    System.out.println("0 - Volver");
    System.out.print("Opcion: ");

    return scanner.nextInt();
    }
    public int mostrarMenuProductos() {

    System.out.println("\n=========================");
    System.out.println(" GESTION DE PRODUCTOS ");
    System.out.println("=========================");
    System.out.println("1 - Crear producto");
    System.out.println("2 - Listar productos");
    System.out.println("3 - Buscar producto");
    System.out.println("4 - Eliminar producto");
    System.out.println("0 - Volver");
    System.out.print("Opcion: ");

    return scanner.nextInt();
    }
    public int mostrarMenuUsuarios() {

    System.out.println("\n=========================");
    System.out.println(" GESTION DE USUARIOS ");
    System.out.println("=========================");
    System.out.println("1 - Crear usuario");
    System.out.println("2 - Listar usuarios");
    System.out.println("3 - Buscar usuario");
    System.out.println("4 - Eliminar usuario");
    System.out.println("0 - Volver");
    System.out.print("Opcion: ");

    return scanner.nextInt();
    }
    public int mostrarMenuPedidos() {

    System.out.println("\n=========================");
    System.out.println(" GESTION DE PEDIDOS ");
    System.out.println("=========================");
    System.out.println("1 - Crear pedido");
    System.out.println("2 - Listar pedidos");
    System.out.println("3 - Agregar producto a pedido");
    System.out.println("4 - Ver detalle pedido");
    System.out.println("5 - Eliminar pedido");
    System.out.println("0 - Volver");
    System.out.print("Opcion: ");

    return scanner.nextInt();
    }
}