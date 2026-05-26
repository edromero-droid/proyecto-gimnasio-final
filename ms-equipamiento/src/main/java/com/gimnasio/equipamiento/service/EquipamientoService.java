package com.gimnasio.equipamiento.service;
import com.gimnasio.equipamiento.dto.EquipamientoDTO;
import com.gimnasio.equipamiento.entity.Equipamiento;
import com.gimnasio.equipamiento.repository.EquipamientoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor @Slf4j
public class EquipamientoService {
    private final EquipamientoRepository repo;

    public List<EquipamientoDTO> listarDisponibles() {
        log.info("[EquipamientoService] Listando equipamiento disponible");
        return repo.findByEstado("DISPONIBLE").stream().map(this::toDTO).collect(Collectors.toList());
    }
    public List<EquipamientoDTO> listarTodos() {
        log.info("[EquipamientoService] Listando todo el equipamiento");
        return repo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }
    public EquipamientoDTO buscarPorId(Long id) {
        log.info("[EquipamientoService] Buscando equipo con ID: {}", id);
        return repo.findById(id).map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Equipo con ID " + id + " no encontrado"));
    }
    public EquipamientoDTO crear(EquipamientoDTO dto) {
        log.info("[EquipamientoService] Creando equipo: {}", dto.getNombre());
        // Verificar código duplicado con mensaje claro
        if (repo.findByCodigo(dto.getCodigo()).isPresent()) {
            throw new RuntimeException("Ya existe un equipo con el código: " + dto.getCodigo());
        }
        Equipamiento e = new Equipamiento();
        e.setCodigo(dto.getCodigo()); e.setNombre(dto.getNombre()); e.setDescripcion(dto.getDescripcion());
        e.setTipo(dto.getTipo()); e.setMarca(dto.getMarca()); e.setModelo(dto.getModelo());
        e.setSalaNombre(dto.getSalaNombre()); e.setEstado("DISPONIBLE");
        EquipamientoDTO creado = toDTO(repo.save(e));
        log.info("[EquipamientoService] Equipo creado con ID: {}", creado.getEquipoId());
        return creado;
    }
    public EquipamientoDTO actualizar(Long id, EquipamientoDTO dto) {
        log.info("[EquipamientoService] Actualizando equipo con ID: {}", id);
        Equipamiento e = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipo con ID " + id + " no encontrado"));
        // Verificar que el código no esté en uso por otro equipo
        repo.findByCodigo(dto.getCodigo()).ifPresent(existing -> {
            if (!existing.getEquipoId().equals(id)) {
                throw new RuntimeException("Ya existe un equipo con el código: " + dto.getCodigo());
            }
        });
        e.setCodigo(dto.getCodigo()); e.setNombre(dto.getNombre()); e.setDescripcion(dto.getDescripcion());
        e.setTipo(dto.getTipo()); e.setMarca(dto.getMarca()); e.setModelo(dto.getModelo());
        e.setSalaNombre(dto.getSalaNombre());
        if (dto.getEstado() != null) e.setEstado(dto.getEstado());
        if (dto.getFechaUltimoMantenimiento() != null) e.setFechaUltimoMantenimiento(dto.getFechaUltimoMantenimiento());
        if (dto.getFechaProximoMantenimiento() != null) e.setFechaProximoMantenimiento(dto.getFechaProximoMantenimiento());
        log.info("[EquipamientoService] Equipo ID {} actualizado", id);
        return toDTO(repo.save(e));
    }
    public void eliminar(Long id) {
        log.info("[EquipamientoService] Eliminando equipo con ID: {}", id);
        if (!repo.existsById(id)) throw new RuntimeException("Equipo con ID " + id + " no encontrado");
        repo.deleteById(id);
        log.info("[EquipamientoService] Equipo ID {} eliminado", id);
    }
    private EquipamientoDTO toDTO(Equipamiento e) {
        return EquipamientoDTO.builder()
            .equipoId(e.getEquipoId()).codigo(e.getCodigo()).nombre(e.getNombre())
            .descripcion(e.getDescripcion()).tipo(e.getTipo()).marca(e.getMarca()).modelo(e.getModelo())
            .salaNombre(e.getSalaNombre()).estado(e.getEstado())
            .fechaUltimoMantenimiento(e.getFechaUltimoMantenimiento())
            .fechaProximoMantenimiento(e.getFechaProximoMantenimiento())
            .createdAt(e.getCreatedAt()).build();
    }
}
