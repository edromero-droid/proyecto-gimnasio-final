package com.gimnasio.pagos.controller;
import com.gimnasio.pagos.dto.PagoDTO;
import com.gimnasio.pagos.service.PagoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/pagos") @RequiredArgsConstructor
public class PagoController {
    private final PagoService pagoService;

    @GetMapping
    public ResponseEntity<List<PagoDTO>> listarTodos() {
        return ResponseEntity.ok(pagoService.listarTodos());
    }
    @GetMapping("/{id}")
    public ResponseEntity<PagoDTO> buscarPorId(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(pagoService.buscarPorId(id));
    }
    @GetMapping("/socio/{socioId}")
    public ResponseEntity<List<PagoDTO>> listarPorSocio(@PathVariable(name = "socioId") Long socioId) {
        return ResponseEntity.ok(pagoService.listarPorSocio(socioId));
    }
    @PostMapping
    public ResponseEntity<PagoDTO> registrar(@Valid @RequestBody PagoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pagoService.registrar(dto));
    }
    @PatchMapping("/{id}/anular")
    public ResponseEntity<PagoDTO> anular(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(pagoService.anular(id));
    }
}
