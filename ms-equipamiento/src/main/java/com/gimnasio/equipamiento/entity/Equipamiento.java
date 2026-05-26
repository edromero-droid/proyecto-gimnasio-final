package com.gimnasio.equipamiento.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity @Table(name = "equipamiento") @Getter @Setter @NoArgsConstructor
public class Equipamiento {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "equipo_id") private Long equipoId;
    @NotBlank(message = "El código es obligatorio") @Column(nullable = false, unique = true, length = 50) private String codigo;
    @NotBlank(message = "El nombre es obligatorio") @Column(nullable = false, length = 150) private String nombre;
    @Column(length = 500) private String descripcion;
    @NotBlank(message = "El tipo es obligatorio") @Column(nullable = false, length = 100) private String tipo;
    @Column(length = 100) private String marca;
    @Column(length = 100) private String modelo;
    @NotBlank(message = "La sala es obligatoria") @Column(name = "sala_nombre", nullable = false, length = 100) private String salaNombre;
    @Column(nullable = false, length = 30) private String estado = "DISPONIBLE";
    @Column(name = "fecha_ultimo_mantenimiento") private LocalDate fechaUltimoMantenimiento;
    @Column(name = "fecha_proximo_mantenimiento") private LocalDate fechaProximoMantenimiento;
    @Column(name = "created_at") private LocalDateTime createdAt = LocalDateTime.now();
}
