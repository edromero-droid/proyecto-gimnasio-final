package com.gimnasio.clases.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sesiones_clase",
    indexes = {
        @Index(name = "idx_sesion_estado", columnList = "estado"),
        @Index(name = "idx_sesion_instructor", columnList = "instructor_id")
    }
)
@Getter @Setter @NoArgsConstructor
public class SesionClase {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sesion_id")
    private Long sesionId;

    @NotBlank(message = "El nombre de la clase es obligatorio")
    @Column(name = "clase_nombre", nullable = false, length = 150)
    private String claseNombre;

    @Column(name = "clase_tipo", length = 100)
    private String claseTipo;

    @Column(name = "clase_nivel", length = 30)
    private String claseNivel = "TODOS";

    @Column(name = "duracion_minutos")
    private Integer duracionMinutos;

    @NotNull(message = "El instructor es obligatorio")
    @Column(name = "instructor_id")
    private Long instructorId;

    @Column(name = "instructor_nombre", length = 200)
    private String instructorNombre;

    @NotBlank(message = "La sala es obligatoria")
    @Column(name = "sala_nombre", nullable = false, length = 100)
    private String salaNombre;

    @NotNull(message = "La fecha de inicio es obligatoria")
    @Column(name = "fecha_hora_inicio")
    private LocalDateTime fechaHoraInicio;

    @NotNull(message = "La fecha de fin es obligatoria")
    @Column(name = "fecha_hora_fin")
    private LocalDateTime fechaHoraFin;

    @Column(name = "capacidad_maxima")
    private Integer capacidadMaxima = 20;

    @Column(name = "reservas_activas")
    private Integer reservasActivas = 0;

    @Column(nullable = false, length = 30)
    private String estado = "PROGRAMADA";

    @Column(name = "costo_adicional", precision = 10, scale = 2)
    private BigDecimal costoAdicional = BigDecimal.ZERO;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    // Relación inversa: una sesión puede tener muchas reservas
    @OneToMany(mappedBy = "sesion", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ReservaClase> reservas = new ArrayList<>();
}
