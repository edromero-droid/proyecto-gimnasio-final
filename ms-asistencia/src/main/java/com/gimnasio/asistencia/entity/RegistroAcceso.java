package com.gimnasio.asistencia.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "registros_acceso",
    indexes = {
        @Index(name = "idx_acceso_socio", columnList = "socio_id"),
        @Index(name = "idx_acceso_timestamp", columnList = "timestamp_evento"),
        @Index(name = "idx_acceso_permitido", columnList = "permitido")
    }
)
@Getter @Setter @NoArgsConstructor
public class RegistroAcceso {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "registro_id") private Long registroId;

    @Column(name = "socio_id", nullable = false)
    private Long socioId;

    @Column(name = "socio_nombre", nullable = false, length = 200)
    private String socioNombre;

    @Column(name = "socio_email", length = 255)
    private String socioEmail;

    @Column(name = "plan_nombre", length = 150)
    private String planNombre;

    @Column(name = "membresia_fin")
    private LocalDate membresiaFin;

    @Column(name = "tipo_acceso", nullable = false, length = 20)
    private String tipoAcceso = "ENTRADA";

    @Column(name = "metodo_acceso", length = 30)
    private String metodoAcceso;

    @Column(name = "punto_acceso", length = 100)
    private String puntoAcceso = "ENTRADA_PRINCIPAL";

    @Column(nullable = false)
    private Boolean permitido = true;

    @Column(name = "motivo_denegacion", length = 300)
    private String motivoDenegacion;

    @Column(name = "timestamp_evento", nullable = false)
    private LocalDateTime timestampEvento = LocalDateTime.now();

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
