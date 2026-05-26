package com.gimnasio.socios.service;

import com.gimnasio.socios.dto.SocioDTO;
import com.gimnasio.socios.entity.Socio;
import com.gimnasio.socios.repository.SocioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SocioService {

    private final SocioRepository socioRepository;

    public SocioService(SocioRepository socioRepository) {
        this.socioRepository = socioRepository;
    }

    public List<SocioDTO> listarTodos() {
        log.info("[SocioService] Listando todos los socios");
        List<SocioDTO> socios = socioRepository.findAll()
                .stream().map(this::convertirADTO).collect(Collectors.toList());
        log.info("[SocioService] Se encontraron {} socios", socios.size());
        return socios;
    }

    public Optional<SocioDTO> buscarPorId(Long id) {
        log.info("[SocioService] Buscando socio con ID: {}", id);
        Optional<SocioDTO> resultado = socioRepository.findById(id).map(this::convertirADTO);
        if (resultado.isEmpty()) log.warn("[SocioService] Socio con ID {} no encontrado", id);
        return resultado;
    }

    public Optional<SocioDTO> buscarPorEmail(String email) {
        log.info("[SocioService] Buscando socio con email: {}", email);
        return socioRepository.findByEmail(email).map(this::convertirADTO);
    }

    public List<SocioDTO> listarPorEstado(String estado) {
        log.info("[SocioService] Listando socios con estado: {}", estado);
        return socioRepository.findByEstado(estado)
                .stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    public SocioDTO crear(SocioDTO dto) {
        log.info("[SocioService] Creando nuevo socio con email: {}", dto.getEmail());
        if (socioRepository.existsByEmail(dto.getEmail())) {
            log.warn("[SocioService] Email duplicado: {}", dto.getEmail());
            throw new RuntimeException("Ya existe un socio con el email: " + dto.getEmail());
        }
        Socio guardado = socioRepository.save(convertirAEntidad(dto));
        log.info("[SocioService] Socio creado con ID: {}", guardado.getId());
        return convertirADTO(guardado);
    }

    public Optional<SocioDTO> actualizar(Long id, SocioDTO dto) {
        log.info("[SocioService] Actualizando socio con ID: {}", id);
        return socioRepository.findById(id).map(socio -> {
            socio.setNombre(dto.getNombre());
            socio.setApellido(dto.getApellido());
            socio.setTelefono(dto.getTelefono());
            socio.setDireccion(dto.getDireccion());
            socio.setFechaNacimiento(dto.getFechaNacimiento());
            socio.setTipoDocumento(dto.getTipoDocumento());
            socio.setNumeroDocumento(dto.getNumeroDocumento());
            if (dto.getEstado() != null) socio.setEstado(dto.getEstado());
            if (dto.getNivelMembresia() != null) socio.setNivelMembresia(dto.getNivelMembresia());
            SocioDTO actualizado = convertirADTO(socioRepository.save(socio));
            log.info("[SocioService] Socio ID {} actualizado correctamente", id);
            return actualizado;
        });
    }

    public boolean eliminar(Long id) {
        log.info("[SocioService] Eliminando socio con ID: {}", id);
        if (socioRepository.existsById(id)) {
            socioRepository.deleteById(id);
            log.info("[SocioService] Socio ID {} eliminado", id);
            return true;
        }
        log.warn("[SocioService] Socio ID {} no encontrado para eliminar", id);
        return false;
    }

    private SocioDTO convertirADTO(Socio socio) {
        SocioDTO dto = new SocioDTO();
        dto.setId(socio.getId());
        dto.setNombre(socio.getNombre());
        dto.setApellido(socio.getApellido());
        dto.setEmail(socio.getEmail());
        dto.setTelefono(socio.getTelefono());
        dto.setFechaNacimiento(socio.getFechaNacimiento());
        dto.setTipoDocumento(socio.getTipoDocumento());
        dto.setNumeroDocumento(socio.getNumeroDocumento());
        dto.setDireccion(socio.getDireccion());
        dto.setEstado(socio.getEstado());
        dto.setNivelMembresia(socio.getNivelMembresia());
        dto.setCreatedAt(socio.getCreatedAt());
        return dto;
    }

    private Socio convertirAEntidad(SocioDTO dto) {
        Socio socio = new Socio();
        socio.setNombre(dto.getNombre());
        socio.setApellido(dto.getApellido());
        socio.setEmail(dto.getEmail());
        socio.setTelefono(dto.getTelefono());
        socio.setFechaNacimiento(dto.getFechaNacimiento());
        socio.setTipoDocumento(dto.getTipoDocumento());
        socio.setNumeroDocumento(dto.getNumeroDocumento());
        socio.setDireccion(dto.getDireccion());
        if (dto.getEstado() != null) socio.setEstado(dto.getEstado());
        if (dto.getNivelMembresia() != null) socio.setNivelMembresia(dto.getNivelMembresia());
        return socio;
    }
}
