package com.gimnasio.asistencia.controller;
import com.gimnasio.asistencia.dto.RegistroAccesoDTO;
import com.gimnasio.asistencia.service.AsistenciaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/asistencia") @RequiredArgsConstructor
public class AsistenciaController {
    private final AsistenciaService service;

    @GetMapping
    public ResponseEntity<List<RegistroAccesoDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }
    @PostMapping("/acceso")
    public ResponseEntity<RegistroAccesoDTO> registrar(@Valid @RequestBody RegistroAccesoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrarAcceso(dto));
    }
    @GetMapping("/historial/{socioId}")
    public ResponseEntity<List<RegistroAccesoDTO>> historial(@PathVariable(name = "socioId") Long socioId) {
        return ResponseEntity.ok(service.historialSocio(socioId));
    }
    @GetMapping("/rechazados")
    public ResponseEntity<List<RegistroAccesoDTO>> rechazados() {
        return ResponseEntity.ok(service.accesosRechazados());
    }
}
