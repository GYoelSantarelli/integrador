package integrador.entities;

import java.time.LocalDateTime;

public abstract class Base {

    Long id;
    private boolean eliminado;
    private LocalDateTime createdAt;


    public Base() {
        this.createdAt = LocalDateTime.now();
        this.eliminado = false;
    }

    public Base(Long id) {
        this();
        setId(id);
    }
    
    // getters y setters
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {

        if (id != null && id < 0) {
            throw new IllegalArgumentException(
                    "El ID no puede ser negativo."
            );
        }

        this.id = id;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {

    if (createdAt == null) {
        throw new IllegalArgumentException(
                "La fecha de creación no puede ser nula."
        );
    }

    this.createdAt = createdAt;
}
    @Override
    public abstract String toString();
}
