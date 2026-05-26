package com.gimnasio.pagos.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "pagos",
    indexes = {
        @Index(name = "idx_pago_socio", columnList = "socio_id"),
        @Index(name = "idx_pago_estado", columnList = "estado"),
        @Index(name = "idx_pago_fecha", columnList = "fecha_pago")
    }
)
@Getter @Setter @NoArgsConstructor
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pago_id")
    private Long pagoId;

    @NotNull(message = "El ID del socio es obligatorio")
    @Column(name = "socio_id", nullable = false)
    private Long socioId;

    @NotBlank(message = "El nombre del socio es obligatorio")
    @Column(name = "socio_nombre", nullable = false, length = 100)
    private String socioNombre;

    @Column(name = "socio_email", length = 255)
    private String socioEmail;

    @Column(name = "plan_id")
    private Long planId;

    @Column(name = "plan_nombre", length = 150)
    private String planNombre;

    @NotBlank(message = "El tipo de pago es obligatorio")
    @Column(name = "tipo_pago", nullable = false, length = 50)
    private String tipoPago;

    @Column(length = 300)
    private String concepto;

    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto debe ser positivo")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal monto;

    @Column(length = 10)
    private String moneda = "CLP";

    @Column(name = "metodo_pago", length = 50)
    private String metodoPago;

    @Column(nullable = false, length = 30)
    private String estado = "COMPLETADO";

    @Column(name = "periodo_inicio")
    private LocalDate periodoInicio;

    @Column(name = "periodo_fin")
    private LocalDate periodoFin;

    @Column(name = "numero_boleta", length = 50)
    private String numeroBoleta;

    @Column(name = "fecha_pago", nullable = false)
    private LocalDateTime fechaPago = LocalDateTime.now();

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
