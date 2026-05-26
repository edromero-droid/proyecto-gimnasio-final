package com.gimnasio.clases.service;
import com.gimnasio.clases.dto.*;
import com.gimnasio.clases.entity.*;
import com.gimnasio.clases.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.util.*;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor @Slf4j
public class ClaseService {
    private final SesionClaseRepository sesionRepo;
    private final ReservaClaseRepository reservaRepo;
    private final RestTemplate restTemplate;

    @Value("${ms.socios.url}")
    private String sociosUrl;

    private SocioClientDTO obtenerYValidarSocio(Long socioId, String emailVerif, String nombreVerif, String docVerif) {
        try {
            log.info("[ClaseService] Consultando socio ID: {} en ms-socios", socioId);
            SocioClientDTO socio = restTemplate.getForObject(
                sociosUrl + "/api/socios/" + socioId, SocioClientDTO.class);
            if (socio == null) throw new RuntimeException("No existe un socio registrado con ID: " + socioId);
            if (!"ACTIVO".equals(socio.getEstado()))
                throw new RuntimeException("El socio con ID " + socioId + " no está activo. Estado: " + socio.getEstado());
            socio.validarDatos(emailVerif, nombreVerif, docVerif);
            log.info("[ClaseService] Socio validado: {}", socio.getNombreCompleto());
            return socio;
        } catch (org.springframework.web.client.HttpClientErrorException e) {
            log.warn("[ClaseService] Socio ID {} no existe", socioId);
            throw new RuntimeException("No existe un socio registrado con ID: " + socioId);
        }
    }

    // Verificar si socio existe sin validar datos adicionales
    private void verificarSocioExiste(Long socioId) {
        try {
            SocioClientDTO socio = restTemplate.getForObject(
                sociosUrl + "/api/socios/" + socioId, SocioClientDTO.class);
            if (socio == null) throw new RuntimeException("No existe un socio registrado con ID: " + socioId);
        } catch (org.springframework.web.client.HttpClientErrorException e) {
            throw new RuntimeException("No existe un socio registrado con ID: " + socioId);
        }
    }

    public List<SesionClaseDTO> listarDisponibles() {
        log.info("[ClaseService] Listando sesiones disponibles");
        return sesionRepo.findByEstado("PROGRAMADA").stream().map(this::toSesionDTO).collect(Collectors.toList());
    }
    public List<SesionClaseDTO> listarTodas() {
        log.info("[ClaseService] Listando todas las sesiones");
        return sesionRepo.findAll().stream().map(this::toSesionDTO).collect(Collectors.toList());
    }
    public SesionClaseDTO buscarPorId(Long id) {
        log.info("[ClaseService] Buscando sesión con ID: {}", id);
        return sesionRepo.findById(id).map(this::toSesionDTO)
                .orElseThrow(() -> new RuntimeException("Sesión con ID " + id + " no encontrada"));
    }
    public SesionClaseDTO programar(SesionClaseDTO dto) {
        log.info("[ClaseService] Programando sesión: {}", dto.getClaseNombre());
        SesionClase s = new SesionClase();
        s.setClaseNombre(dto.getClaseNombre()); s.setClaseTipo(dto.getClaseTipo());
        s.setClaseNivel(dto.getClaseNivel() != null ? dto.getClaseNivel() : "TODOS");
        s.setDuracionMinutos(dto.getDuracionMinutos());
        s.setInstructorId(dto.getInstructorId()); s.setInstructorNombre(dto.getInstructorNombre());
        s.setSalaNombre(dto.getSalaNombre());
        s.setFechaHoraInicio(dto.getFechaHoraInicio()); s.setFechaHoraFin(dto.getFechaHoraFin());
        s.setCapacidadMaxima(dto.getCapacidadMaxima() != null ? dto.getCapacidadMaxima() : 20);
        s.setCostoAdicional(dto.getCostoAdicional() != null ? dto.getCostoAdicional() : java.math.BigDecimal.ZERO);
        SesionClaseDTO creada = toSesionDTO(sesionRepo.save(s));
        log.info("[ClaseService] Sesión creada con ID: {}", creada.getSesionId());
        return creada;
    }
    public ReservaClaseDTO reservar(ReservaClaseDTO dto) {
        SocioClientDTO socio = obtenerYValidarSocio(
            dto.getSocioId(), dto.getSocioEmailVerificacion(),
            dto.getSocioNombreVerificacion(), dto.getSocioDocumentoVerificacion());
        log.info("[ClaseService] Reservando sesión ID: {} para: {}", dto.getSesionId(), socio.getNombreCompleto());
        SesionClase sesion = sesionRepo.findById(dto.getSesionId())
                .orElseThrow(() -> new RuntimeException("Sesión con ID " + dto.getSesionId() + " no encontrada"));
        if (!"PROGRAMADA".equals(sesion.getEstado()))
            throw new RuntimeException("La sesión no está disponible. Estado actual: " + sesion.getEstado());
        if (sesion.getReservasActivas() >= sesion.getCapacidadMaxima()) {
            log.warn("[ClaseService] Sesión ID {} sin cupos", dto.getSesionId());
            throw new RuntimeException("La sesión está llena. Capacidad máxima: " + sesion.getCapacidadMaxima());
        }
        ReservaClase r = new ReservaClase();
        r.setSesion(sesion); r.setClaseNombre(sesion.getClaseNombre());
        r.setInstructorNombre(sesion.getInstructorNombre()); r.setFechaHoraInicio(sesion.getFechaHoraInicio());
        r.setSocioId(dto.getSocioId()); r.setSocioNombre(socio.getNombreCompleto());
        r.setSocioEmail(socio.getEmail()); r.setEstado("CONFIRMADA");
        ReservaClase guardada = reservaRepo.save(r);
        sesion.setReservasActivas(sesion.getReservasActivas() + 1);
        sesionRepo.save(sesion);
        log.info("[ClaseService] Reserva creada con ID: {}", guardada.getReservaId());
        return toReservaDTO(guardada);
    }

    public List<ReservaClaseDTO> reservasPorSocio(Long socioId) {
        log.info("[ClaseService] Consultando reservas del socio ID: {}", socioId);
        // Verificar si el socio existe
        verificarSocioExiste(socioId);
        // Buscar reservas
        List<ReservaClaseDTO> reservas = reservaRepo.findBySocioId(socioId)
                .stream().map(this::toReservaDTO).collect(Collectors.toList());
        if (reservas.isEmpty()) {
            throw new RuntimeException("El socio con ID " + socioId + " no tiene reservas registradas");
        }
        return reservas;
    }

    private SesionClaseDTO toSesionDTO(SesionClase s) {
        return SesionClaseDTO.builder()
            .sesionId(s.getSesionId()).claseNombre(s.getClaseNombre()).claseTipo(s.getClaseTipo())
            .claseNivel(s.getClaseNivel()).duracionMinutos(s.getDuracionMinutos())
            .instructorId(s.getInstructorId()).instructorNombre(s.getInstructorNombre())
            .salaNombre(s.getSalaNombre()).fechaHoraInicio(s.getFechaHoraInicio()).fechaHoraFin(s.getFechaHoraFin())
            .capacidadMaxima(s.getCapacidadMaxima()).reservasActivas(s.getReservasActivas())
            .estado(s.getEstado()).costoAdicional(s.getCostoAdicional()).createdAt(s.getCreatedAt()).build();
    }
    private ReservaClaseDTO toReservaDTO(ReservaClase r) {
        return ReservaClaseDTO.builder()
            .reservaId(r.getReservaId()).sesionId(r.getSesionId()).claseNombre(r.getClaseNombre())
            .instructorNombre(r.getInstructorNombre()).fechaHoraInicio(r.getFechaHoraInicio())
            .socioId(r.getSocioId()).socioNombre(r.getSocioNombre()).socioEmail(r.getSocioEmail())
            .estado(r.getEstado()).fechaReserva(r.getFechaReserva()).createdAt(r.getCreatedAt()).build();
    }
}
