package com.gimnasio.planes.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "planes",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_plan_nombre", columnNames = "nombre")
    },
    indexes = {
        @Index(name = "idx_plan_activo", columnList = "activo")
    }
)
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del plan es obligatorio")
    @Column(nullable = false, unique = true, length = 100)
    private String nombre;

    @Column(length = 500)
    private String descripcion;

    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser positivo")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @NotNull(message = "La duración en días es obligatoria")
    @Column(name = "dias_duracion", nullable = false)
    private Integer diasDuracion;

    @Column(name = "acceso_clases", nullable = false)
    private boolean accesoClases;

    @Column(name = "acceso_piscina", nullable = false)
    private boolean accesoPiscina;

    @Column(nullable = false)
    private boolean activo = true;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.activo = true;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }
    public Integer getDiasDuracion() { return diasDuracion; }
    public void setDiasDuracion(Integer diasDuracion) { this.diasDuracion = diasDuracion; }
    public boolean isAccesoClases() { return accesoClases; }
    public void setAccesoClases(boolean accesoClases) { this.accesoClases = accesoClases; }
    public boolean isAccesoPiscina() { return accesoPiscina; }
    public void setAccesoPiscina(boolean accesoPiscina) { this.accesoPiscina = accesoPiscina; }
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
