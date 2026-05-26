package com.gimnasio.instructores.controller;
import com.gimnasio.instructores.dto.InstructorDTO;
import com.gimnasio.instructores.service.InstructorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController @RequestMapping("/api/instructores") @RequiredArgsConstructor
public class InstructorController {
    private final InstructorService service;

    @GetMapping
    public ResponseEntity<List<InstructorDTO>> listar(@RequestParam(name = "todos", defaultValue = "false") boolean todos) {
        return ResponseEntity.ok(todos ? service.listarTodos() : service.listarActivos());
    }
    @GetMapping("/{id}")
    public ResponseEntity<InstructorDTO> buscar(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }
    @PostMapping
    public ResponseEntity<InstructorDTO> crear(@Valid @RequestBody InstructorDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(dto));
    }
    @PutMapping("/{id}")
    public ResponseEntity<InstructorDTO> actualizar(@PathVariable(name = "id") Long id, @Valid @RequestBody InstructorDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String,String>> baja(@PathVariable(name = "id") Long id) {
        service.darDeBaja(id);
        return ResponseEntity.ok(Map.of("mensaje", "Instructor dado de baja correctamente"));
    }
}
