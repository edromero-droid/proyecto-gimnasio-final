package com.gimnasio.asistencia.service;
import com.gimnasio.asistencia.dto.RegistroAccesoDTO;
import com.gimnasio.asistencia.dto.SocioClientDTO;
import com.gimnasio.asistencia.entity.RegistroAcceso;
import com.gimnasio.asistencia.repository.RegistroAccesoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor @Slf4j
public class AsistenciaService {
    private final RegistroAccesoRepository repo;
    private final RestTemplate restTemplate;

    @Value("${ms.socios.url}")
    private String sociosUrl;

    private SocioClientDTO obtenerYValidarSocio(Long socioId, String emailVerif, String nombreVerif, String docVerif) {
        try {
            log.info("[AsistenciaService] Consultando socio ID: {} en ms-socios", socioId);
            SocioClientDTO socio = restTemplate.getForObject(
                sociosUrl + "/api/socios/" + socioId, SocioClientDTO.class);
            if (socio == null) throw new RuntimeException("No existe un socio registrado con ID: " + socioId);
            socio.validarDatos(emailVerif, nombreVerif, docVerif);
            log.info("[AsistenciaService] Socio validado: {}", socio.getNombreCompleto());
            return socio;
        } catch (org.springframework.web.client.HttpClientErrorException e) {
            log.warn("[AsistenciaService] Socio ID {} no existe", socioId);
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

    public RegistroAccesoDTO registrarAcceso(RegistroAccesoDTO dto) {
        SocioClientDTO socio = obtenerYValidarSocio(
            dto.getSocioId(), dto.getSocioEmailVerificacion(),
            dto.getSocioNombreVerificacion(), dto.getSocioDocumentoVerificacion());
        log.info("[AsistenciaService] Registrando acceso para: {}", socio.getNombreCompleto());
        boolean permitido = true;
        String motivo = null;
        if (!"ACTIVO".equals(socio.getEstado())) {
            permitido = false; motivo = "SOCIO_INACTIVO";
            log.warn("[AsistenciaService] Acceso denegado - SOCIO_INACTIVO ID: {}", dto.getSocioId());
        }
        LocalDate membresiaFin = socio.getMembresiaFin();
        if (permitido && membresiaFin != null && membresiaFin.isBefore(LocalDate.now())) {
            permitido = false; motivo = "MEMBRESIA_VENCIDA";
            log.warn("[AsistenciaService] Acceso denegado - MEMBRESIA_VENCIDA ID: {}", dto.getSocioId());
        }
        RegistroAcceso registro = new RegistroAcceso();
        registro.setSocioId(dto.getSocioId());
        registro.setSocioNombre(socio.getNombreCompleto());
        registro.setSocioEmail(socio.getEmail());
        registro.setPlanNombre(socio.getNivelMembresia());
        registro.setMembresiaFin(membresiaFin);
        registro.setTipoAcceso(dto.getTipoAcceso() != null ? dto.getTipoAcceso() : "ENTRADA");
        registro.setMetodoAcceso(dto.getMetodoAcceso());
        registro.setPuntoAcceso(dto.getPuntoAcceso() != null ? dto.getPuntoAcceso() : "ENTRADA_PRINCIPAL");
        registro.setPermitido(permitido);
        registro.setMotivoDenegacion(motivo);
        registro.setTimestampEvento(LocalDateTime.now());
        RegistroAccesoDTO resultado = toDTO(repo.save(registro));
        log.info("[AsistenciaService] Acceso registrado ID: {} - permitido: {}", resultado.getRegistroId(), permitido);
        return resultado;
    }

    public List<RegistroAccesoDTO> historialSocio(Long socioId) {
        log.info("[AsistenciaService] Historial del socio ID: {}", socioId);
        // Verificar si socio existe
        verificarSocioExiste(socioId);
        List<RegistroAccesoDTO> historial = repo.findBySocioIdOrderByTimestampEventoDesc(socioId)
                .stream().map(this::toDTO).collect(Collectors.toList());
        if (historial.isEmpty()) {
            throw new RuntimeException("El socio con ID " + socioId + " no tiene registros de acceso");
        }
        return historial;
    }

    public List<RegistroAccesoDTO> accesosRechazados() {
        log.info("[AsistenciaService] Consultando accesos rechazados");
        return repo.findByPermitidoFalseOrderByTimestampEventoDesc().stream().map(this::toDTO).collect(Collectors.toList());
    }
    public List<RegistroAccesoDTO> listarTodos() {
        log.info("[AsistenciaService] Listando todos los registros");
        return repo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }
    private RegistroAccesoDTO toDTO(RegistroAcceso r) {
        return RegistroAccesoDTO.builder()
            .registroId(r.getRegistroId()).socioId(r.getSocioId())
            .socioNombre(r.getSocioNombre()).socioEmail(r.getSocioEmail())
            .planNombre(r.getPlanNombre()).membresiaFin(r.getMembresiaFin())
            .tipoAcceso(r.getTipoAcceso()).metodoAcceso(r.getMetodoAcceso())
            .puntoAcceso(r.getPuntoAcceso()).permitido(r.getPermitido())
            .motivoDenegacion(r.getMotivoDenegacion()).timestampEvento(r.getTimestampEvento())
            .createdAt(r.getCreatedAt()).build();
    }
}
