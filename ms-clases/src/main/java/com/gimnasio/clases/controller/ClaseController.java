package com.gimnasio.clases.controller;
import com.gimnasio.clases.dto.*;
import com.gimnasio.clases.service.ClaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/clases") @RequiredArgsConstructor
public class ClaseController {
    private final ClaseService service;

    @GetMapping("/sesiones/disponibles")
    public ResponseEntity<List<SesionClaseDTO>> disponibles() {
        return ResponseEntity.ok(service.listarDisponibles());
    }
    @GetMapping("/sesiones")
    public ResponseEntity<List<SesionClaseDTO>> todas() {
        return ResponseEntity.ok(service.listarTodas());
    }
    @GetMapping("/sesiones/{id}")
    public ResponseEntity<SesionClaseDTO> buscar(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }
    @PostMapping("/sesiones")
    public ResponseEntity<SesionClaseDTO> programar(@Valid @RequestBody SesionClaseDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.programar(dto));
    }
    @PostMapping("/reservas")
    public ResponseEntity<ReservaClaseDTO> reservar(@Valid @RequestBody ReservaClaseDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.reservar(dto));
    }
    @GetMapping("/reservas/socio/{socioId}")
    public ResponseEntity<List<ReservaClaseDTO>> reservasSocio(@PathVariable(name = "socioId") Long socioId) {
        return ResponseEntity.ok(service.reservasPorSocio(socioId));
    }
}
