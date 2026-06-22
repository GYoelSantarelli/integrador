package integrador.entities;

import integrador.enums.Rol;

import java.util.ArrayList;
import java.util.List;

public class Usuario extends Base {

    private String nombre;
    private String apellido;
    private String mail;
    private String celular;
    private String contrasenia;
    private Rol rol;
    private List<Pedido> pedidos;

    public Usuario() {
        super();
        this.pedidos = new ArrayList<>();
    }

    public Usuario(Long id,
                   String nombre,
                   String apellido,
                   String mail,
                   String celular,
                   String contrasenia,
                   Rol rol) {

        super(id);

        setNombre(nombre);
        setApellido(apellido);
        setMail(mail);
        setContrasenia(contrasenia);

        this.celular = celular;
        setRol(rol);
        this.pedidos = new ArrayList<>();
    }

    public void agregarPedido(Pedido pedido) {

        if (pedido == null) {
            return;
        }

        if (!pedidos.contains(pedido)) {

            pedidos.add(pedido);

            if (pedido.getUsuario() != this) {
                pedido.setUsuario(this);
            }
        }
    }

    public double obtenerTotalAcumulado() {

        double acumulado = 0.0;

        for (Pedido pedido : pedidos) {
            acumulado += pedido.getTotal();
        }

        return acumulado;
    }

    // getters y setters

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {

        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El nombre no puede estar vacío."
            );
        }

        this.nombre = nombre.trim();
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {

        if (apellido == null || apellido.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El apellido no puede estar vacío."
            );
        }

        this.apellido = apellido.trim();
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {

        if (mail == null || mail.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El email no puede estar vacío."
            );
        }

        if (!mail.contains("@")) {
            throw new IllegalArgumentException(
                    "Email inválido."
            );
        }

        this.mail = mail.trim().toLowerCase();
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {

        if (contrasenia == null ||
                contrasenia.trim().length() < 4) {

            throw new IllegalArgumentException(
                    "La contraseña debe tener al menos 4 caracteres."
            );
        }

        this.contrasenia = contrasenia.trim();
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {

        if (rol == null) {
            throw new IllegalArgumentException(
                    "El rol no puede ser nulo."
            );
        }

        this.rol = rol;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {

        this.pedidos =
                (pedidos != null)
                        ? pedidos
                        : new ArrayList<>();
    }

    @Override
    public String toString() {

        String rolTexto =
                (rol != null)
                        ? rol.name()
                        : "SIN_ROL";

        return String.format(
                "USUARIO [%d] | %s %s | Mail: %s | Rol: %s | Pedidos: %d",
                getId(),
                nombre,
                apellido,
                mail,
                rolTexto,
                pedidos.size()
        );
    }
}