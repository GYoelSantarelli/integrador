/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package integrado.prog2.ui;

/**
 *
 * @author rocio
 */
import integrado.prog2.entities.Usuario;
import integrado.prog2.enums.Rol;
import integrado.prog2.service.UsuarioService;

import java.util.List;
import java.util.Scanner;

public class UsuarioMenu {

    private final UsuarioService usuarioService;
    private final Scanner scanner;

    public UsuarioMenu(UsuarioService usuarioService, Scanner scanner) {
        this.usuarioService = usuarioService;
        this.scanner = scanner;
    }

    public void mostrar() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n___ GESTION DE USUARIOS ___");
            System.out.println("1. Listar usuarios");
            System.out.println("2. Crear usuario");
            System.out.println("3. Editar usuario");
            System.out.println("4. Eliminar usuario");
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
        List<Usuario> lista = usuarioService.listarActivos();
        System.out.println("\n___ USUARIOS ___");
        if (lista.isEmpty()) System.out.println("No hay usuarios registrados.");
        else lista.forEach(System.out::println);
    }

    private void crear() {
        System.out.println("\n___ CREAR USUARIO ___");
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine().trim();
        System.out.print("Apellido: ");
        String apellido = scanner.nextLine().trim();
        System.out.print("Mail: ");
        String mail = scanner.nextLine().trim();
        System.out.print("Celular: ");
        String celular = scanner.nextLine().trim();
        System.out.print("Contraseña: ");
        String contrasena = scanner.nextLine().trim();
        Rol rol = seleccionarRol();
        try {
            Usuario nuevo = usuarioService.crear(nombre, apellido, mail, celular, contrasena, rol);
            System.out.println(" Usuario creado con ID: " + nuevo.getId());
        } catch (Exception e) {
            System.out.println(" Error: " + e.getMessage());
        }
    }

    private void editar() {
        listar();
        System.out.println("\n___ EDITAR USUARIO ___");
        System.out.print("ID del usuario: ");
        Long id = leerLong();
        if (id == null) return;
        try {
            Usuario u = usuarioService.buscarPorId(id);
            System.out.println("Editando: " + u);
            System.out.print("Nuevo nombre (Enter para mantener): ");
            String nombre = scanner.nextLine().trim();
            System.out.print("Nuevo apellido (Enter para mantener): ");
            String apellido = scanner.nextLine().trim();
            System.out.print("Nuevo mail (Enter para mantener): ");
            String mail = scanner.nextLine().trim();
            System.out.print("Nuevo celular (Enter para mantener): ");
            String celular = scanner.nextLine().trim();
            System.out.print("Nueva contraseña (Enter para mantener): ");
            String contrasena = scanner.nextLine().trim();
            System.out.print("Nuevo rol (1=ADMIN, 2=USUARIO, Enter para mantener): ");
            String rolStr = scanner.nextLine().trim();
            Rol rol = rolStr.equals("1") ? Rol.ADMIN : rolStr.equals("2") ? Rol.USUARIO : null;
            usuarioService.editar(id,
                    nombre.isEmpty() ? null : nombre,
                    apellido.isEmpty() ? null : apellido,
                    mail.isEmpty() ? null : mail,
                    celular.isEmpty() ? null : celular,
                    contrasena.isEmpty() ? null : contrasena,
                    rol);
            System.out.println(" Usuario actualizado.");
        } catch (Exception e) {
            System.out.println(" Error: " + e.getMessage());
        }
    }

    private void eliminar() {
        listar();
        System.out.println("\n___ ELIMINAR USUARIO ___");
        System.out.print("ID del usuario: ");
        Long id = leerLong();
        if (id == null) return;
        try {
            Usuario u = usuarioService.buscarPorId(id);
            System.out.print("¿Confirma eliminar a '" + u.getNombre() + " " + u.getApellido() + "'? (S/N): ");
            if (scanner.nextLine().trim().equalsIgnoreCase("S")) {
                usuarioService.eliminar(id);
                System.out.println(" Usuario eliminado (baja logica).");
            } else {
                System.out.println("Operacion cancelada.");
            }
        } catch (Exception e) {
            System.out.println(" Error: " + e.getMessage());
        }
    }

    private Rol seleccionarRol() {
        System.out.println("Rol: 1=ADMIN  2=USUARIO");
        System.out.print("Seleccione: ");
        String op = scanner.nextLine().trim();
        return op.equals("1") ? Rol.ADMIN : Rol.USUARIO;
    }

    private int leerEntero() {
        try { return Integer.parseInt(scanner.nextLine().trim()); }
        catch (NumberFormatException e) { return -1; }
    }

    private Long leerLong() {
        try { return Long.parseLong(scanner.nextLine().trim()); }
        catch (NumberFormatException e) { System.out.println("✘ ID inválido."); return null; }
    }
}