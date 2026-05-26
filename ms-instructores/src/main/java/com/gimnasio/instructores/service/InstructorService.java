package com.gimnasio.instructores.service;
import com.gimnasio.instructores.dto.InstructorDTO;
import com.gimnasio.instructores.entity.Instructor;
import com.gimnasio.instructores.repository.InstructorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor @Slf4j
public class InstructorService {
    private final InstructorRepository repo;

    public List<InstructorDTO> listarActivos() {
        log.info("[InstructorService] Listando instructores activos");
        return repo.findByEstado("ACTIVO").stream().map(this::toDTO).collect(Collectors.toList());
    }
    public List<InstructorDTO> listarTodos() {
        log.info("[InstructorService] Listando todos los instructores");
        return repo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }
    public InstructorDTO buscarPorId(Long id) {
        log.info("[InstructorService] Buscando instructor con ID: {}", id);
        return repo.findById(id).map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Instructor con ID " + id + " no encontrado"));
    }
    public InstructorDTO crear(InstructorDTO dto) {
        log.info("[InstructorService] Creando instructor: {} {}", dto.getNombre(), dto.getApellido());
        // Verificar email duplicado con mensaje claro
        if (repo.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Ya existe un instructor con el email: " + dto.getEmail());
        }
        Instructor i = new Instructor();
        i.setNombre(dto.getNombre()); i.setApellido(dto.getApellido()); i.setEmail(dto.getEmail());
        i.setTelefono(dto.getTelefono()); i.setFechaNacimiento(dto.getFechaNacimiento());
        i.setTipoContrato(dto.getTipoContrato()); i.setFechaIngreso(dto.getFechaIngreso());
        i.setEspecialidades(dto.getEspecialidades());
        i.setAnosExperiencia(dto.getAnosExperiencia() != null ? dto.getAnosExperiencia() : 0);
        i.setBiografia(dto.getBiografia()); i.setFotoUrl(dto.getFotoUrl());
        InstructorDTO creado = toDTO(repo.save(i));
        log.info("[InstructorService] Instructor creado con ID: {}", creado.getInstructorId());
        return creado;
    }
    public InstructorDTO actualizar(Long id, InstructorDTO dto) {
        log.info("[InstructorService] Actualizando instructor con ID: {}", id);
        Instructor i = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Instructor con ID " + id + " no encontrado"));
        i.setNombre(dto.getNombre()); i.setApellido(dto.getApellido());
        i.setTelefono(dto.getTelefono()); i.setEspecialidades(dto.getEspecialidades());
        i.setAnosExperiencia(dto.getAnosExperiencia()); i.setBiografia(dto.getBiografia());
        i.setEstado(dto.getEstado()); i.setFotoUrl(dto.getFotoUrl());
        log.info("[InstructorService] Instructor ID {} actualizado", id);
        return toDTO(repo.save(i));
    }
    public void darDeBaja(Long id) {
        log.info("[InstructorService] Dando de baja instructor con ID: {}", id);
        Instructor i = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Instructor con ID " + id + " no encontrado"));
        i.setEstado("INACTIVO"); repo.save(i);
        log.info("[InstructorService] Instructor ID {} dado de baja", id);
    }
    private InstructorDTO toDTO(Instructor i) {
        return InstructorDTO.builder()
            .instructorId(i.getInstructorId()).nombre(i.getNombre()).apellido(i.getApellido())
            .email(i.getEmail()).telefono(i.getTelefono()).fechaNacimiento(i.getFechaNacimiento())
            .tipoContrato(i.getTipoContrato()).fechaIngreso(i.getFechaIngreso())
            .especialidades(i.getEspecialidades()).anosExperiencia(i.getAnosExperiencia())
            .biografia(i.getBiografia()).calificacionPromedio(i.getCalificacionPromedio())
            .totalValoraciones(i.getTotalValoraciones()).estado(i.getEstado())
            .fotoUrl(i.getFotoUrl()).createdAt(i.getCreatedAt()).build();
    }
}
