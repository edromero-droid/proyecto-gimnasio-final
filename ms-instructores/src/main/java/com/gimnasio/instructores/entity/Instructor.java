package com.gimnasio.instructores.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity @Table(name = "instructores") @Getter @Setter @NoArgsConstructor
public class Instructor {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "instructor_id") private Long instructorId;
    @NotBlank(message = "El nombre es obligatorio") @Column(nullable = false, length = 100) private String nombre;
    @NotBlank(message = "El apellido es obligatorio") @Column(nullable = false, length = 100) private String apellido;
    @NotBlank(message = "El email es obligatorio") @Email @Column(nullable = false, unique = true, length = 255) private String email;
    @Column(length = 20) private String telefono;
    @Column(name = "fecha_nacimiento") private LocalDate fechaNacimiento;
    @NotBlank(message = "El tipo de contrato es obligatorio") @Column(name = "tipo_contrato", nullable = false, length = 50) private String tipoContrato;
    @Column(name = "fecha_ingreso") private LocalDate fechaIngreso;
    @Column(columnDefinition = "TEXT") private String especialidades;
    @Column(name = "anos_experiencia") private Integer anosExperiencia = 0;
    @Column(columnDefinition = "TEXT") private String biografia;
    @Column(name = "calificacion_promedio", precision = 3, scale = 2) private BigDecimal calificacionPromedio = BigDecimal.ZERO;
    @Column(name = "total_valoraciones") private Integer totalValoraciones = 0;
    @Column(nullable = false, length = 30) private String estado = "ACTIVO";
    @Column(name = "foto_url", length = 500) private String fotoUrl;
    @Column(name = "created_at") private LocalDateTime createdAt = LocalDateTime.now();
}
