package com.gimnasio.asistencia.dto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RegistroAccesoDTO {
    private Long registroId;

    @NotNull(message = "El ID del socio es obligatorio")
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
    private String planNombre;
    private LocalDate membresiaFin;

    private String tipoAcceso;
    private String metodoAcceso;
    private String puntoAcceso;
    private Boolean permitido;
    private String motivoDenegacion;
    private LocalDateTime timestampEvento;
    private LocalDateTime createdAt;
}
