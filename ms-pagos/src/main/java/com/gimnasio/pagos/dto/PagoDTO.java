package com.gimnasio.pagos.dto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PagoDTO {
    private Long pagoId;

    @NotNull(message = "El ID del socio es obligatorio")
    private Long socioId;

    // Campos de verificación - solo entrada, no se muestran en respuesta
    @JsonIgnore
    private String socioEmailVerificacion;
    @JsonIgnore
    private String socioNombreVerificacion;
    @JsonIgnore
    private String socioDocumentoVerificacion;

    // Campos autocompletados desde ms-socios
    private String socioNombre;
    private String socioEmail;

    private Long planId;
    private String planNombre;

    @NotBlank(message = "El tipo de pago es obligatorio")
    private String tipoPago;

    private String concepto;

    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto debe ser positivo")
    private BigDecimal monto;

    private String moneda;
    private String metodoPago;
    private String estado;
    private LocalDate periodoInicio;
    private LocalDate periodoFin;
    private String numeroBoleta;
    private LocalDateTime fechaPago;
    private LocalDateTime createdAt;
}
