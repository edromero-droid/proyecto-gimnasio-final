package com.gimnasio.notificaciones.service;
import com.gimnasio.notificaciones.dto.NotificacionDTO;
import com.gimnasio.notificaciones.dto.SocioClientDTO;
import com.gimnasio.notificaciones.entity.Notificacion;
import com.gimnasio.notificaciones.repository.NotificacionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor @Slf4j
public class NotificacionService {
    private final NotificacionRepository repo;
    private final RestTemplate restTemplate;

    @Value("${ms.socios.url}")
    private String sociosUrl;

    private SocioClientDTO obtenerYValidarSocio(Long socioId, String emailVerif, String nombreVerif, String docVerif) {
        try {
            log.info("[NotificacionService] Consultando socio ID: {} en ms-socios", socioId);
            SocioClientDTO socio = restTemplate.getForObject(
                sociosUrl + "/api/socios/" + socioId, SocioClientDTO.class);
            if (socio == null) throw new RuntimeException("No existe un socio registrado con ID: " + socioId);
            if (!"ACTIVO".equals(socio.getEstado()))
                throw new RuntimeException("El socio con ID " + socioId + " no está activo");
            socio.validarDatos(emailVerif, nombreVerif, docVerif);
            log.info("[NotificacionService] Socio validado: {}", socio.getNombreCompleto());
            return socio;
        } catch (org.springframework.web.client.HttpClientErrorException e) {
            log.warn("[NotificacionService] Socio ID {} no existe", socioId);
            throw new RuntimeException("No existe un socio registrado con ID: " + socioId);
        }
    }

    private void verificarSocioExiste(Long socioId) {
        try {
            SocioClientDTO socio = restTemplate.getForObject(
                sociosUrl + "/api/socios/" + socioId, SocioClientDTO.class);
            if (socio == null) throw new RuntimeException("No existe un socio registrado con ID: " + socioId);
        } catch (org.springframework.web.client.HttpClientErrorException e) {
            throw new RuntimeException("No existe un socio registrado con ID: " + socioId);
        }
    }

    public NotificacionDTO crear(NotificacionDTO dto) {
        SocioClientDTO socio = obtenerYValidarSocio(
            dto.getDestinatarioId(), dto.getDestinatarioEmailVerificacion(),
            dto.getDestinatarioNombreVerificacion(), dto.getDestinatarioDocumentoVerificacion());
        log.info("[NotificacionService] Creando notificación tipo: {} para: {}", dto.getTipo(), socio.getNombreCompleto());
        Notificacion n = new Notificacion();
        n.setDestinatarioId(dto.getDestinatarioId());
        n.setDestinatarioNombre(socio.getNombreCompleto());
        n.setDestinatarioEmail(socio.getEmail());
        n.setTipo(dto.getTipo());
        n.setCanal(dto.getCanal() != null ? dto.getCanal() : "EMAIL");
        n.setAsunto(dto.getAsunto()); n.setCuerpo(dto.getCuerpo());
        n.setOrigenEvento(dto.getOrigenEvento());
        n.setProgramadaPara(dto.getProgramadaPara() != null ? dto.getProgramadaPara() : LocalDateTime.now());
        n.setEstado("PENDIENTE");
        Notificacion guardada = repo.save(n);
        log.info("[NotificacionService] Notificación creada ID: {}", guardada.getNotificacionId());
        return toDTO(guardada);
    }

    public List<NotificacionDTO> listarPorDestinatario(Long destinatarioId) {
        log.info("[NotificacionService] Listando notificaciones del destinatario ID: {}", destinatarioId);
        // Verificar si el socio existe
        verificarSocioExiste(destinatarioId);
        List<NotificacionDTO> notificaciones = repo.findByDestinatarioId(destinatarioId)
                .stream().map(this::toDTO).collect(Collectors.toList());
        if (notificaciones.isEmpty()) {
            throw new RuntimeException("El socio con ID " + destinatarioId + " no tiene notificaciones registradas");
        }
        return notificaciones;
    }

    public List<NotificacionDTO> listarPendientes() {
        log.info("[NotificacionService] Listando pendientes");
        return repo.findByEstado("PENDIENTE").stream().map(this::toDTO).collect(Collectors.toList());
    }
    public List<NotificacionDTO> listarTodas() {
        log.info("[NotificacionService] Listando todas");
        return repo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }
    private NotificacionDTO toDTO(Notificacion n) {
        return NotificacionDTO.builder()
            .notificacionId(n.getNotificacionId()).destinatarioId(n.getDestinatarioId())
            .destinatarioNombre(n.getDestinatarioNombre()).destinatarioEmail(n.getDestinatarioEmail())
            .tipo(n.getTipo()).canal(n.getCanal()).asunto(n.getAsunto()).cuerpo(n.getCuerpo())
            .estado(n.getEstado()).origenEvento(n.getOrigenEvento())
            .programadaPara(n.getProgramadaPara()).enviadaEn(n.getEnviadaEn())
            .createdAt(n.getCreatedAt()).build();
    }
}
