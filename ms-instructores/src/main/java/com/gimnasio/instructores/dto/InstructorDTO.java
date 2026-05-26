package com.gimnasio.instructores.dto;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class InstructorDTO {
    private Long instructorId;
    @NotBlank(message = "El nombre es obligatorio") private String nombre;
    @NotBlank(message = "El apellido es obligatorio") private String apellido;
    @NotBlank(message = "El email es obligatorio") @Email(message = "El email debe tener formato válido") private String email;
    private String telefono;
    private LocalDate fechaNacimiento;
    @NotBlank(message = "El tipo de contrato es obligatorio") private String tipoContrato;
    private LocalDate fechaIngreso;
    @NotBlank(message = "Las especialidades son obligatorias") private String especialidades;
    private Integer anosExperiencia;
    private String biografia;
    private BigDecimal calificacionPromedio;
    private Integer totalValoraciones;
    private String estado;
    private String fotoUrl;
    private LocalDateTime createdAt;
}
