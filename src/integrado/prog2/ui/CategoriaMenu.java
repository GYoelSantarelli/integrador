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
import integrado.prog2.service.CategoriaService;
import integrado.prog2.service.ProductoService;

import java.util.List;
import java.util.Scanner;

public class CategoriaMenu {

    private final CategoriaService categoriaService;
    private final ProductoService productoService;
    private final Scanner scanner;

    public CategoriaMenu(CategoriaService categoriaService, ProductoService productoService, Scanner scanner) {
        this.categoriaService = categoriaService;
        this.productoService = productoService;
        this.scanner = scanner;
    }

    public void mostrar() {
        boolean volver = false;
        while (!volver) {
            System.out.println("GESTION DE CATEGORIAS ");
            System.out.println("______________________");
            System.out.println("1. Listar categorias");
            System.out.println("2. Crear categoria");
            System.out.println("3. Editar categoria");
            System.out.println("4. Eliminar categoria");
            System.out.println("0. Volver");
            System.out.print("Seleccione: ");
            int opcion = leerEntero();
            switch (opcion) {
                case 1 -> listar();
                case 2 -> crear();
                case 3 -> editar();
                case 4 -> eliminar();
                case 0 -> volver = true;
                default -> System.out.println(" Opcion invalida.");
            }
        }
    }

    private void listar() {
        List<Categoria> lista = categoriaService.listarActivas();
        System.out.println("\n___ CATEGORIAS ___");
        if (lista.isEmpty()) System.out.println("No hay categorias cargadas.");
        else lista.forEach(System.out::println);
    }

    private void crear() {
        System.out.println("\n___ CREAR CATEGORIA ___");
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine().trim();
        System.out.print("Descripcion: ");
        String descripcion = scanner.nextLine().trim();
        try {
            Categoria nueva = categoriaService.crear(nombre, descripcion);
            System.out.println(" Categoria creada con ID: " + nueva.getId());
        } catch (Exception e) {
            System.out.println(" Error: " + e.getMessage());
        }
    }

    private void editar() {
        listar();
        System.out.println("\n___ EDITAR CATEGORÍA ___");
        System.out.print("ID de la categoría: ");
        Long id = leerLong();
        if (id == null) return;
        try {
            Categoria c = categoriaService.buscarPorId(id);
            System.out.println("Editando: " + c);
            System.out.print("Nuevo nombre (Enter para mantener): ");
            String nombre = scanner.nextLine().trim();
            System.out.print("Nueva descripción (Enter para mantener): ");
            String descripcion = scanner.nextLine().trim();
            categoriaService.editar(id,
                    nombre.isEmpty() ? null : nombre,
                    descripcion.isEmpty() ? null : descripcion);
            System.out.println(" Categoría actualizada.");
        } catch (Exception e) {
            System.out.println(" Error: " + e.getMessage());
        }
    }

    private void eliminar() {
        listar();
        System.out.println("\n___ ELIMINAR CATEGORÍA ___");
        System.out.print("ID de la categoria: ");
        Long id = leerLong();
        if (id == null) return;
        try {
            Categoria c = categoriaService.buscarPorId(id);
            if (categoriaService.tieneProductosActivos(id, productoService)) {
                System.out.println(" La categoria tiene productos activos. No se puede eliminar.");
                return;
            }
            System.out.print("¿Confirma eliminar '" + c.getNombre() + "'? (S/N): ");
            if (scanner.nextLine().trim().equalsIgnoreCase("S")) {
                categoriaService.eliminar(id);
                System.out.println(" Categoria eliminada (baja logica).");
            } else {
                System.out.println("Operacion cancelada.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private int leerEntero() {
        try { return Integer.parseInt(scanner.nextLine().trim()); }
        catch (NumberFormatException e) { return -1; }
    }

    private Long leerLong() {
        try { return Long.parseLong(scanner.nextLine().trim()); }
        catch (NumberFormatException e) { System.out.println(" ID invalido."); return null; }
    }
}
