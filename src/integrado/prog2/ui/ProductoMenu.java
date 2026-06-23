/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package integrado.prog2.ui;

/**
 *
 * @author rocio
 */
import integrado.prog2.entities.Categoria;
import integrado.prog2.entities.Producto;
import integrado.prog2.service.CategoriaService;
import integrado.prog2.service.ProductoService;

import java.util.List;
import java.util.Scanner;

public class ProductoMenu {

    private final ProductoService productoService;
    private final CategoriaService categoriaService;
    private final Scanner scanner;

    public ProductoMenu(ProductoService productoService, CategoriaService categoriaService, Scanner scanner) {
        this.productoService = productoService;
        this.categoriaService = categoriaService;
        this.scanner = scanner;
    }

    public void mostrar() {
        boolean volver = false;
        while (!volver) {
            System.out.println("     GESTIÓN DE PRODUCTOS   ");
            System.out.println("\n__________________________");
            System.out.println("1. Listar productos");
            System.out.println("2. Crear producto");
            System.out.println("3. Editar producto");
            System.out.println("4. Eliminar producto");
            System.out.println("0. Volver");
            System.out.print("Seleccione: ");
            int opcion = leerEntero();
            switch (opcion) {
                case 1 -> listar();
                case 2 -> crear();
                case 3 -> editar();
                case 4 -> eliminar();
                case 0 -> volver = true;
                default -> System.out.println("Opcion invalida.");
            }
        }
    }

    private void listar() {
        System.out.println("\n1. Todos  2. Por categoría");
        System.out.print("Opcion: ");
        int op = leerEntero();
        List<Producto> lista;
        if (op == 2) {
            mostrarCategorias();
            System.out.print("ID de categoria: ");
            Long catId = leerLong();
            if (catId == null) return;
            lista = productoService.listarActivosPorCategoria(catId);
        } else {
            lista = productoService.listarActivos();
        }
        System.out.println("\n___PRODUCTOS ___");
        if (lista.isEmpty()) System.out.println("No hay productos.");
        else lista.forEach(System.out::println);
    }

    private void crear() {
        System.out.println("\n___ CREAR PRODUCTO ___");
        mostrarCategorias();
        System.out.print("ID de categoria: ");
        Long catId = leerLong();
        if (catId == null) return;
        try {
            Categoria cat = categoriaService.buscarPorId(catId);
            System.out.print("Nombre: ");
            String nombre = scanner.nextLine().trim();
            System.out.print("Descripcion: ");
            String descripcion = scanner.nextLine().trim();
            System.out.print("Precio: ");
            Double precio = leerDouble();
            if (precio == null) return;
            System.out.print("Stock: ");
            Integer stock = leerEnteroNoNegativo();
            if (stock == null) return;
            System.out.print("Imagen (Enter para omitir): ");
            String imagen = scanner.nextLine().trim();
            System.out.print("¿Disponible? (S/N): ");
            boolean disponible = scanner.nextLine().trim().equalsIgnoreCase("S");
            Producto nuevo = productoService.crear(nombre, precio, descripcion, stock,
                    imagen.isEmpty() ? "sin_imagen" : imagen, disponible, cat);
            System.out.println(" Producto creado con ID: " + nuevo.getId());
        } catch (Exception e) {
            System.out.println(" Error: " + e.getMessage());
        }
    }

    private void editar() {
        productoService.listarActivos().forEach(System.out::println);
        System.out.println("\n___ EDITAR PRODUCTO ___");
        System.out.print("ID del producto: ");
        Long id = leerLong();
        if (id == null) return;
        try {
            Producto p = productoService.buscarPorId(id);
            System.out.println("Editando: " + p);
            System.out.print("Nuevo nombre (Enter para mantener): ");
            String nombre = scanner.nextLine().trim();
            System.out.print("Nuevo precio (Enter para mantener): ");
            String precioStr = scanner.nextLine().trim();
            System.out.print("Nueva descripcion (Enter para mantener): ");
            String descripcion = scanner.nextLine().trim();
            System.out.print("Nuevo stock (Enter para mantener): ");
            String stockStr = scanner.nextLine().trim();
            System.out.print("Nueva imagen (Enter para mantener): ");
            String imagen = scanner.nextLine().trim();
            System.out.print("¿Disponible? (S/N/Enter para mantener): ");
            String dispStr = scanner.nextLine().trim();
            System.out.print("Nueva categoria por ID (Enter para mantener): ");
            String catStr = scanner.nextLine().trim();

            Double precio = precioStr.isEmpty() ? null : Double.parseDouble(precioStr);
            Integer stock = stockStr.isEmpty() ? null : Integer.parseInt(stockStr);
            Boolean disponible = dispStr.isEmpty() ? null : dispStr.equalsIgnoreCase("S");
            Categoria cat = catStr.isEmpty() ? null : categoriaService.buscarPorId(Long.parseLong(catStr));

            productoService.editar(id,
                    nombre.isEmpty() ? null : nombre, precio,
                    descripcion.isEmpty() ? null : descripcion,
                    stock, imagen.isEmpty() ? null : imagen, disponible, cat);
            System.out.println(" Producto actualizado.");
        } catch (Exception e) {
            System.out.println(" Error: " + e.getMessage());
        }
    }

    private void eliminar() {
        productoService.listarActivos().forEach(System.out::println);
        System.out.println("\n___ ELIMINAR PRODUCTO ___");
        System.out.print("ID del producto: ");
        Long id = leerLong();
        if (id == null) return;
        try {
            Producto p = productoService.buscarPorId(id);
            System.out.print("¿Confirma eliminar '" + p.getNombre() + "'? (S/N): ");
            if (scanner.nextLine().trim().equalsIgnoreCase("S")) {
                productoService.eliminar(id);
                System.out.println(" Producto eliminado (baja lógica).");
            } else {
                System.out.println("Operacion cancelada.");
            }
        } catch (Exception e) {
            System.out.println(" Error: " + e.getMessage());
        }
    }

    private void mostrarCategorias() {
        List<Categoria> cats = categoriaService.listarActivas();
        System.out.println("Categorias disponibles:");
        if (cats.isEmpty()) System.out.println("  Sin categorias.");
        else cats.forEach(c -> System.out.println("  " + c));
    }

    private int leerEntero() {
        try { return Integer.parseInt(scanner.nextLine().trim()); }
        catch (NumberFormatException e) { return -1; }
    }

    private Integer leerEnteroNoNegativo() {
        try {
            int v = Integer.parseInt(scanner.nextLine().trim());
            if (v < 0) { System.out.println(" No puede ser negativo."); return null; }
            return v;
        } catch (NumberFormatException e) { System.out.println(" Numero invalido."); return null; }
    }

    private Double leerDouble() {
        try {
            double v = Double.parseDouble(scanner.nextLine().trim());
            if (v < 0) { System.out.println(" No puede ser negativo."); return null; }
            return v;
        } catch (NumberFormatException e) { System.out.println(" Numero invalido."); return null; }
    }

    private Long leerLong() {
        try { return Long.parseLong(scanner.nextLine().trim()); }
        catch (NumberFormatException e) { System.out.println(" ID invalido."); return null; }
    }
}