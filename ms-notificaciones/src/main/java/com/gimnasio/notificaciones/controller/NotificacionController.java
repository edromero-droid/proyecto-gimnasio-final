package com.gimnasio.notificaciones.controller;
import com.gimnasio.notificaciones.dto.NotificacionDTO;
import com.gimnasio.notificaciones.service.NotificacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController @RequestMapping("/api/notificaciones") @RequiredArgsConstructor
public class NotificacionController {
    private final NotificacionService service;

    @GetMapping
    public ResponseEntity<List<NotificacionDTO>> listarTodas() {
        return ResponseEntity.ok(service.listarTodas());
    }
    @GetMapping("/pendientes")
    public ResponseEntity<List<NotificacionDTO>> pendientes() {
        return ResponseEntity.ok(service.listarPendientes());
    }
    @GetMapping("/destinatario/{id}")
    public ResponseEntity<List<NotificacionDTO>> porDestinatario(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(service.listarPorDestinatario(id));
    }
    @PostMapping
    public ResponseEntity<NotificacionDTO> crear(@Valid @RequestBody NotificacionDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(dto));
    }
}
