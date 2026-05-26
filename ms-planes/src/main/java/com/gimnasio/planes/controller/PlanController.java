package com.gimnasio.planes.controller;
import com.gimnasio.planes.dto.PlanDTO;
import com.gimnasio.planes.service.PlanService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/planes")
public class PlanController {
    private final PlanService planService;
    public PlanController(PlanService planService) { this.planService = planService; }

    @GetMapping
    public ResponseEntity<List<PlanDTO>> listarTodos() {
        return ResponseEntity.ok(planService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable(name = "id") Long id) {
        return planService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new RuntimeException("Plan con ID " + id + " no encontrado"));
    }

    @PostMapping
    public ResponseEntity<PlanDTO> crear(@Valid @RequestBody PlanDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(planService.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable(name = "id") Long id, @RequestBody PlanDTO dto) {
        return planService.actualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new RuntimeException("Plan con ID " + id + " no encontrado"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> eliminar(@PathVariable(name = "id") Long id) {
        if (planService.eliminar(id))
            return ResponseEntity.ok(Map.of("mensaje", "Plan con ID " + id + " eliminado correctamente"));
        throw new RuntimeException("Plan con ID " + id + " no encontrado");
    }
}
