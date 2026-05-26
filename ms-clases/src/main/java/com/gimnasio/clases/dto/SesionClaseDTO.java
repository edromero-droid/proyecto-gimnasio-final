package com.gimnasio.clases.dto;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SesionClaseDTO {
    private Long sesionId;
    @NotBlank(message = "El nombre de la clase es obligatorio") private String claseNombre;
    private String claseTipo;
    private String claseNivel;
    private Integer duracionMinutos;
    @NotNull(message = "El instructor es obligatorio") private Long instructorId;
    private String instructorNombre;
    @NotBlank(message = "La sala es obligatoria") private String salaNombre;
    @NotNull(message = "La fecha de inicio es obligatoria") private LocalDateTime fechaHoraInicio;
    @NotNull(message = "La fecha de fin es obligatoria") private LocalDateTime fechaHoraFin;
    private Integer capacidadMaxima;
    private Integer reservasActivas;
    private String estado;
    private BigDecimal costoAdicional;
    private LocalDateTime createdAt;
}
