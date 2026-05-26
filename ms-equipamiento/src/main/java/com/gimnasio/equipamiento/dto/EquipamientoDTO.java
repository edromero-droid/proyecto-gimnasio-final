package com.gimnasio.equipamiento.dto;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class EquipamientoDTO {
    private Long equipoId;
    @NotBlank(message = "El código es obligatorio") private String codigo;
    @NotBlank(message = "El nombre es obligatorio") private String nombre;
    private String descripcion;
    @NotBlank(message = "El tipo es obligatorio") private String tipo;
    private String marca;
    private String modelo;
    @NotBlank(message = "La sala es obligatoria") private String salaNombre;
    private String estado;
    private LocalDate fechaUltimoMantenimiento;
    private LocalDate fechaProximoMantenimiento;
    private LocalDateTime createdAt;
}
