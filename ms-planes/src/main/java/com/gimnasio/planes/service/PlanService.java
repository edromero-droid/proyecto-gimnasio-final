package com.gimnasio.planes.service;

import com.gimnasio.planes.dto.PlanDTO;
import com.gimnasio.planes.entity.Plan;
import com.gimnasio.planes.repository.PlanRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PlanService {

    private final PlanRepository planRepository;

    public PlanService(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    public List<PlanDTO> listarTodos() {
        log.info("[PlanService] Listando todos los planes");
        List<PlanDTO> planes = planRepository.findAll().stream().map(this::convertirADTO).collect(Collectors.toList());
        log.info("[PlanService] Se encontraron {} planes", planes.size());
        return planes;
    }

    public Optional<PlanDTO> buscarPorId(Long id) {
        log.info("[PlanService] Buscando plan con ID: {}", id);
        Optional<PlanDTO> resultado = planRepository.findById(id).map(this::convertirADTO);
        if (resultado.isEmpty()) log.warn("[PlanService] Plan con ID {} no encontrado", id);
        return resultado;
    }

    public PlanDTO crear(PlanDTO dto) {
        log.info("[PlanService] Creando nuevo plan: {}", dto.getNombre());
        Plan plan = new Plan();
        plan.setNombre(dto.getNombre());
        plan.setDescripcion(dto.getDescripcion());
        plan.setPrecio(dto.getPrecio());
        plan.setDiasDuracion(dto.getDiasDuracion());
        plan.setAccesoClases(dto.isAccesoClases());
        plan.setAccesoPiscina(dto.isAccesoPiscina());
        Plan guardado = planRepository.save(plan);
        log.info("[PlanService] Plan creado con ID: {}", guardado.getId());
        return convertirADTO(guardado);
    }

    public Optional<PlanDTO> actualizar(Long id, PlanDTO dto) {
        log.info("[PlanService] Actualizando plan con ID: {}", id);
        return planRepository.findById(id).map(plan -> {
            plan.setNombre(dto.getNombre());
            plan.setDescripcion(dto.getDescripcion());
            plan.setPrecio(dto.getPrecio());
            plan.setDiasDuracion(dto.getDiasDuracion());
            plan.setAccesoClases(dto.isAccesoClases());
            plan.setAccesoPiscina(dto.isAccesoPiscina());
            PlanDTO actualizado = convertirADTO(planRepository.save(plan));
            log.info("[PlanService] Plan ID {} actualizado correctamente", id);
            return actualizado;
        });
    }

    public boolean eliminar(Long id) {
        log.info("[PlanService] Eliminando plan con ID: {}", id);
        if (planRepository.existsById(id)) {
            planRepository.deleteById(id);
            log.info("[PlanService] Plan ID {} eliminado", id);
            return true;
        }
        log.warn("[PlanService] Plan ID {} no encontrado para eliminar", id);
        return false;
    }

    private PlanDTO convertirADTO(Plan plan) {
        PlanDTO dto = new PlanDTO();
        dto.setId(plan.getId());
        dto.setNombre(plan.getNombre());
        dto.setDescripcion(plan.getDescripcion());
        dto.setPrecio(plan.getPrecio());
        dto.setDiasDuracion(plan.getDiasDuracion());
        dto.setAccesoClases(plan.isAccesoClases());
        dto.setAccesoPiscina(plan.isAccesoPiscina());
        dto.setActivo(plan.isActivo());
        return dto;
    }
}
