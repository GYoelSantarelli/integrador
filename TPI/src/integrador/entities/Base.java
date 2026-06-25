package integrador.entities;

import java.time.LocalDateTime;

public abstract class Base {

    private static Long contador = 1L;

    private final Long id;
    private final LocalDateTime createdAt;
    private boolean eliminado;

    public Base() {
        this.id = contador++;
        this.createdAt = LocalDateTime.now();
        this.eliminado = false;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    @Override
    public abstract String toString();
}