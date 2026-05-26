package com.gimnasio.clases.dto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ReservaClaseDTO {
    private Long reservaId;

    @NotNull(message = "La sesión es obligatoria")
    private Long sesionId;

    private String claseNombre;
    private String instructorNombre;
    private LocalDateTime fechaHoraInicio;

    @NotNull(message = "El socio es obligatorio")
    private Long socioId;

    // Campos de verificación - solo entrada, no se muestran en respuesta
    @JsonIgnore
    private String socioEmailVerificacion;
    @JsonIgnore
    private String socioNombreVerificacion;
    @JsonIgnore
    private String socioDocumentoVerificacion;

    // Autocompletados desde ms-socios
    private String socioNombre;
    private String socioEmail;

    private String estado;
    private LocalDateTime fechaReserva;
    private LocalDateTime createdAt;
}
