package com.gimnasio.clases.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservas_clase",
    indexes = {
        @Index(name = "idx_reserva_sesion", columnList = "sesion_id"),
        @Index(name = "idx_reserva_socio", columnList = "socio_id")
    }
)
@Getter @Setter @NoArgsConstructor
public class ReservaClase {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reserva_id")
    private Long reservaId;

    // Relación real ManyToOne con SesionClase
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sesion_id", nullable = false,
        foreignKey = @ForeignKey(name = "fk_reserva_sesion"))
    private SesionClase sesion;

    @Column(name = "clase_nombre", length = 150)
    private String claseNombre;

    @Column(name = "instructor_nombre", length = 200)
    private String instructorNombre;

    @Column(name = "fecha_hora_inicio")
    private LocalDateTime fechaHoraInicio;

    @Column(name = "socio_id", nullable = false)
    private Long socioId;

    @Column(name = "socio_nombre", nullable = false, length = 200)
    private String socioNombre;

    @Column(name = "socio_email", length = 255)
    private String socioEmail;

    @Column(nullable = false, length = 30)
    private String estado = "CONFIRMADA";

    @Column(name = "fecha_reserva")
    private LocalDateTime fechaReserva = LocalDateTime.now();

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    // Helper para mantener compatibilidad con código que usa sesionId
    public Long getSesionId() {
        return sesion != null ? sesion.getSesionId() : null;
    }
}
