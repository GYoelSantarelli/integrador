/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package integrado.prog2.service;

/**
 *
 * @author rocio
 */
import integrado.prog2.entities.Usuario;
import integrado.prog2.enums.Rol;
import integrado.prog2.exception.EntidadNoEncontradaException;
import integrado.prog2.exception.ValidacionException;

import java.util.ArrayList;
import java.util.List;

public class UsuarioService {

    private final List<Usuario> usuarios = new ArrayList<>();

    public List<Usuario> listarActivos() {
        List<Usuario> activos = new ArrayList<>();
        for (Usuario u : usuarios) {
            if (!u.isEliminado()) activos.add(u);
        }
        return activos;
    }

    public Usuario buscarPorId(Long id) {
        for (Usuario u : usuarios) {
            if (u.getId().equals(id) && !u.isEliminado()) return u;
        }
        throw new EntidadNoEncontradaException("Usuario con ID " + id + " no encontrado o eliminado.");
    }

    public Usuario crear(String nombre, String apellido, String mail, String celular,
                         String contrasena, Rol rol) {
        validarCamposObligatorios(nombre, apellido, mail);
        validarMailUnico(mail, null);
        Usuario nuevo = new Usuario(nombre, apellido, mail, celular, contrasena, rol);
        usuarios.add(nuevo);
        return nuevo;
    }

    public void editar(Long id, String nombre, String apellido, String mail,
                       String celular, String contrasena, Rol rol) {
        Usuario u = buscarPorId(id);
        if (nombre != null && !nombre.isBlank()) u.setNombre(nombre);
        if (apellido != null && !apellido.isBlank()) u.setApellido(apellido);
        if (mail != null && !mail.isBlank()) {
            validarMailUnico(mail, id);
            u.setMail(mail);
        }
        if (celular != null && !celular.isBlank()) u.setCelular(celular);
        if (contrasena != null && !contrasena.isBlank()) u.setContrasena(contrasena);
        if (rol != null) u.setRol(rol);
    }

    public void eliminar(Long id) {
        Usuario u = buscarPorId(id);
        u.setEliminado(true);
    }

    private void validarCamposObligatorios(String nombre, String apellido, String mail) {
        if (nombre == null || nombre.isBlank())
            throw new ValidacionException("El nombre no puede estar vacío.");
        if (apellido == null || apellido.isBlank())
            throw new ValidacionException("El apellido no puede estar vacío.");
        if (mail == null || mail.isBlank())
            throw new ValidacionException("El mail no puede estar vacío.");
    }

    private void validarMailUnico(String mail, Long idExcluir) {
        for (Usuario u : usuarios) {
            if (!u.isEliminado() && u.getMail().equalsIgnoreCase(mail)
                    && !u.getId().equals(idExcluir)) {
                throw new ValidacionException("El mail '" + mail + "' ya está registrado.");
            }
        }
    }
}