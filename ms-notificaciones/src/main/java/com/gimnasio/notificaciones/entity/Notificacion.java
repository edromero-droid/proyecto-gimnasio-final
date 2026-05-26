package com.gimnasio.notificaciones.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notificaciones",
    indexes = {
        @Index(name = "idx_notif_destinatario", columnList = "destinatario_id"),
        @Index(name = "idx_notif_estado", columnList = "estado"),
        @Index(name = "idx_notif_tipo", columnList = "tipo")
    }
)
@Getter @Setter @NoArgsConstructor
public class Notificacion {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notificacion_id") private Long notificacionId;

    @Column(name = "destinatario_id", nullable = false)
    private Long destinatarioId;

    @Column(name = "destinatario_nombre", nullable = false, length = 200)
    private String destinatarioNombre;

    @Column(name = "destinatario_email", length = 255)
    private String destinatarioEmail;

    @Column(nullable = false, length = 100)
    private String tipo;

    @Column(nullable = false, length = 30)
    private String canal = "EMAIL";

    @Column(length = 300)
    private String asunto;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String cuerpo;

    @Column(nullable = false, length = 30)
    private String estado = "PENDIENTE";

    @Column(name = "origen_evento", length = 100)
    private String origenEvento;

    @Column(name = "programada_para")
    private LocalDateTime programadaPara = LocalDateTime.now();

    @Column(name = "enviada_en")
    private LocalDateTime enviadaEn;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
