package com.gimnasio.socios.controller;
import com.gimnasio.socios.dto.SocioDTO;
import com.gimnasio.socios.service.SocioService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/socios")
public class SocioController {

    private final SocioService socioService;
    public SocioController(SocioService socioService) { this.socioService = socioService; }

    @GetMapping
    public ResponseEntity<List<SocioDTO>> listarTodos() {
        return ResponseEntity.ok(socioService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarPorId(@PathVariable(name = "id") Long id) {
        return socioService.buscarPorId(id)
                .map(socio -> ResponseEntity.ok((Object) socio))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("mensaje", "Socio con ID " + id + " no encontrado", "status", 404)));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Object> buscarPorEmail(@PathVariable(name = "email") String email) {
        return socioService.buscarPorEmail(email)
                .map(socio -> ResponseEntity.ok((Object) socio))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("mensaje", "No existe un socio con el email: " + email, "status", 404)));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<SocioDTO>> listarPorEstado(@PathVariable(name = "estado") String estado) {
        return ResponseEntity.ok(socioService.listarPorEstado(estado));
    }

    @PostMapping
    public ResponseEntity<SocioDTO> crear(@Valid @RequestBody SocioDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(socioService.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> actualizar(@PathVariable(name = "id") Long id, @RequestBody SocioDTO dto) {
        return socioService.actualizar(id, dto)
                .map(socio -> ResponseEntity.ok((Object) socio))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("mensaje", "Socio con ID " + id + " no encontrado", "status", 404)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminar(@PathVariable(name = "id") Long id) {
        if (socioService.eliminar(id))
            return ResponseEntity.ok(Map.of("mensaje", "Socio con ID " + id + " eliminado correctamente"));
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("mensaje", "Socio con ID " + id + " no encontrado", "status", 404));
    }
}