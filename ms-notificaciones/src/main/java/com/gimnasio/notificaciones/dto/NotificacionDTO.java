package com.gimnasio.notificaciones.dto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class NotificacionDTO {
    private Long notificacionId;

    @NotNull(message = "El destinatario es obligatorio")
    private Long destinatarioId;

    // Campos de verificación - solo entrada, no se muestran en respuesta
    @JsonIgnore
    private String destinatarioEmailVerificacion;
    @JsonIgnore
    private String destinatarioNombreVerificacion;
    @JsonIgnore
    private String destinatarioDocumentoVerificacion;

    // Autocompletados desde ms-socios
    private String destinatarioNombre;
    private String destinatarioEmail;

    @NotBlank(message = "El tipo es obligatorio")
    private String tipo;

    private String canal;
    private String asunto;

    @NotBlank(message = "El cuerpo del mensaje es obligatorio")
    private String cuerpo;

    private String estado;
    private String origenEvento;
    private LocalDateTime programadaPara;
    private LocalDateTime enviadaEn;
    private LocalDateTime createdAt;
}
