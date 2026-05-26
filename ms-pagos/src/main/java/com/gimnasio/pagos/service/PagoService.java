package com.gimnasio.pagos.service;

import com.gimnasio.pagos.dto.PagoDTO;
import com.gimnasio.pagos.dto.SocioClientDTO;
import com.gimnasio.pagos.entity.Pago;
import com.gimnasio.pagos.repository.PagoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor @Slf4j
public class PagoService {

    private final PagoRepository pagoRepository;
    private final RestTemplate restTemplate;

    @Value("${ms.socios.url}")
    private String sociosUrl;

    private SocioClientDTO obtenerYValidarSocio(Long socioId, String emailVerif, String nombreVerif, String docVerif) {
        try {
            log.info("[PagoService] Consultando socio ID: {} en ms-socios", socioId);
            SocioClientDTO socio = restTemplate.getForObject(
                sociosUrl + "/api/socios/" + socioId, SocioClientDTO.class);
            if (socio == null) throw new RuntimeException("No existe un socio registrado con ID: " + socioId);
            if (!"ACTIVO".equals(socio.getEstado()))
                throw new RuntimeException("El socio con ID " + socioId + " no está activo. Estado actual: " + socio.getEstado());
            socio.validarDatos(emailVerif, nombreVerif, docVerif);
            log.info("[PagoService] Socio validado: {}", socio.getNombreCompleto());
            return socio;
        } catch (org.springframework.web.client.HttpClientErrorException e) {
            log.warn("[PagoService] Socio ID {} no existe", socioId);
            throw new RuntimeException("No existe un socio registrado con ID: " + socioId);
        }
    }

    public List<PagoDTO> listarTodos() {
        log.info("[PagoService] Listando todos los pagos");
        return pagoRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public PagoDTO buscarPorId(Long id) {
        log.info("[PagoService] Buscando pago con ID: {}", id);
        return pagoRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("ID de pago " + id + " no encontrado"));
    }

    public List<PagoDTO> listarPorSocio(Long socioId) {
        log.info("[PagoService] Listando pagos del socio ID: {}", socioId);
        return pagoRepository.findBySocioIdOrderByFechaPagoDesc(socioId)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public PagoDTO registrar(PagoDTO dto) {
        SocioClientDTO socio = obtenerYValidarSocio(
            dto.getSocioId(), dto.getSocioEmailVerificacion(),
            dto.getSocioNombreVerificacion(), dto.getSocioDocumentoVerificacion());
        log.info("[PagoService] Registrando pago para socio: {}", socio.getNombreCompleto());
        Pago pago = new Pago();
        pago.setSocioId(dto.getSocioId());
        pago.setSocioNombre(socio.getNombreCompleto());
        pago.setSocioEmail(socio.getEmail());
        pago.setPlanId(dto.getPlanId()); pago.setPlanNombre(dto.getPlanNombre());
        pago.setTipoPago(dto.getTipoPago()); pago.setConcepto(dto.getConcepto());
        pago.setMonto(dto.getMonto());
        pago.setMoneda(dto.getMoneda() != null ? dto.getMoneda() : "CLP");
        pago.setMetodoPago(dto.getMetodoPago()); pago.setEstado("COMPLETADO");
        pago.setPeriodoInicio(dto.getPeriodoInicio()); pago.setPeriodoFin(dto.getPeriodoFin());
        pago.setNumeroBoleta(dto.getNumeroBoleta());
        Pago guardado = pagoRepository.save(pago);
        log.info("[PagoService] Pago registrado con ID: {}", guardado.getPagoId());
        return toDTO(guardado);
    }

    public PagoDTO anular(Long id) {
        log.info("[PagoService] Anulando pago con ID: {}", id);
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ID de pago " + id + " no encontrado"));
        pago.setEstado("ANULADO");
        return toDTO(pagoRepository.save(pago));
    }

    private PagoDTO toDTO(Pago p) {
        return PagoDTO.builder()
                .pagoId(p.getPagoId()).socioId(p.getSocioId())
                .socioNombre(p.getSocioNombre()).socioEmail(p.getSocioEmail())
                .planId(p.getPlanId()).planNombre(p.getPlanNombre())
                .tipoPago(p.getTipoPago()).concepto(p.getConcepto())
                .monto(p.getMonto()).moneda(p.getMoneda()).metodoPago(p.getMetodoPago())
                .estado(p.getEstado()).periodoInicio(p.getPeriodoInicio())
                .periodoFin(p.getPeriodoFin()).numeroBoleta(p.getNumeroBoleta())
                .fechaPago(p.getFechaPago()).createdAt(p.getCreatedAt()).build();
    }
}
