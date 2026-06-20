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

    public Usuario(Long id, String nombre, String apellido, String mail, String celular, String contrasenia, Rol rol) {
        super(id);
        this.nombre = nombre;
        this.apellido = apellido;
        this.mail = mail;
        this.celular = celular;
        this.contrasenia = contrasenia;
        this.rol = rol;
        this.pedidos = new ArrayList<>();
    }

    public void agregarPedido(Pedido pedido) {
        if (pedido != null) {
            this.pedidos.add(pedido);
            if (pedido.getUsuario() != this) {
                pedido.setUsuario(this);
            }
        }
    }

    public double obtenerTotalAcumulado() {
        double acumulado = 0.0;
        for (Pedido p : pedidos) {
            acumulado += p.getTotal();
        }
        return acumulado;
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getMail() { return mail; }
    public void setMail(String mail) { this.mail = mail; }

    public String getCelular() { return celular; }
    public void setCelular(String celular) { this.celular = celular; }

    public String getContrasenia() { return contrasenia; }
    public void setContrasenia(String contrasenia) { this.contrasenia = contrasenia; }

    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }

    public List<Pedido> getPedidos() { return pedidos; }
    public void setPedidos(List<Pedido> pedidos) { this.pedidos = pedidos; }

    @Override
    public String toString() {
        return String.format("USUARIO: [%s %s] | Mail: [%s] | Rol: [%s]", nombre, apellido, mail, rol.name());
    }
}