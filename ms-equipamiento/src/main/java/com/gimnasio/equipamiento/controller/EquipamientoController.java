package com.gimnasio.equipamiento.controller;
import com.gimnasio.equipamiento.dto.EquipamientoDTO;
import com.gimnasio.equipamiento.service.EquipamientoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController @RequestMapping("/api/equipamiento") @RequiredArgsConstructor
public class EquipamientoController {
    private final EquipamientoService service;

    @GetMapping
    public ResponseEntity<List<EquipamientoDTO>> listar(@RequestParam(name = "todos", defaultValue = "false") boolean todos) {
        return ResponseEntity.ok(todos ? service.listarTodos() : service.listarDisponibles());
    }
    @GetMapping("/{id}")
    public ResponseEntity<EquipamientoDTO> buscar(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }
    @PostMapping
    public ResponseEntity<EquipamientoDTO> crear(@Valid @RequestBody EquipamientoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(dto));
    }
    @PutMapping("/{id}")
    public ResponseEntity<EquipamientoDTO> actualizar(@PathVariable(name = "id") Long id, @Valid @RequestBody EquipamientoDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String,String>> eliminar(@PathVariable(name = "id") Long id) {
        service.eliminar(id);
        return ResponseEntity.ok(Map.of("mensaje", "Equipo eliminado correctamente"));
    }
}
