/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package integrado.prog2.entities;

/**
 *
 * @author rocio
 */


import java.time.LocalDateTime;

public abstract class Base {

    private static long contadorId = 1;

    private Long id;
    private boolean eliminado;
    private LocalDateTime createdAt;

    public Base() {
        this.id = contadorId++;
        this.eliminado = false;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public boolean isEliminado() { return eliminado; }
    public void setEliminado(boolean eliminado) { this.eliminado = eliminado; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Base base = (Base) obj;
        return id.equals(base.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
