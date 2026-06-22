package integrador.service;

import integrador.entities.Usuario;
import integrador.exception.EntidadNoEncontradaException;
import integrador.exception.MailDuplicadoException;

import java.util.ArrayList;
import java.util.List;

public class UsuarioService {

    private List<Usuario> usuarios;

    public UsuarioService() {
        this.usuarios = new ArrayList<>();
    }

    public void crearUsuario(Usuario usuario)
            throws MailDuplicadoException {

        if (usuario == null) {
            throw new IllegalArgumentException(
                    "El usuario no puede ser nulo."
            );
        }

        for (Usuario u : usuarios) {

            if (!u.isEliminado()
                    && u.getMail().equalsIgnoreCase(usuario.getMail())) {

                throw new MailDuplicadoException(
                        "Ya existe un usuario con ese mail."
                );
            }
        }

        usuarios.add(usuario);
    }

    public List<Usuario> listarUsuarios() {

        List<Usuario> activos = new ArrayList<>();

        for (Usuario usuario : usuarios) {

            if (!usuario.isEliminado()) {
                activos.add(usuario);
            }
        }

        return activos;
    }

    public Usuario buscarPorId(Long id)
            throws EntidadNoEncontradaException {

        if (id == null) {
            throw new IllegalArgumentException(
                    "El ID no puede ser nulo."
            );
        }

        for (Usuario usuario : usuarios) {

            if (usuario.getId().equals(id)
                    && !usuario.isEliminado()) {

                return usuario;
            }
        }

        throw new EntidadNoEncontradaException(
                "No existe un usuario con ID: " + id
        );
    }

    public Usuario buscarPorMail(String mail)
            throws EntidadNoEncontradaException {

        if (mail == null || mail.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El mail no puede estar vacío."
            );
        }

        for (Usuario usuario : usuarios) {

            if (!usuario.isEliminado()
                    && usuario.getMail().equalsIgnoreCase(mail)) {

                return usuario;
            }
        }

        throw new EntidadNoEncontradaException(
                "No existe un usuario con mail: " + mail
        );
    }

    public void eliminarUsuario(Long id)
            throws EntidadNoEncontradaException {

        Usuario usuario = buscarPorId(id);

        usuario.setEliminado(true);
    }

    public void actualizarUsuario(
            Long id,
            String nombre,
            String apellido,
            String mail,
            String celular,
            String contrasenia)
            throws EntidadNoEncontradaException {

        Usuario usuario = buscarPorId(id);

        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setMail(mail);
        usuario.setCelular(celular);
        usuario.setContrasenia(contrasenia);
    }

    public int cantidadUsuarios() {
        return listarUsuarios().size();
    }
}