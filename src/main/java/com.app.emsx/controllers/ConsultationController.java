package com.app.emsx.controllers;

import com.app.emsx.common.ApiResponse;
import com.app.emsx.dtos.consultation.ConsultationRequest;
import com.app.emsx.dtos.consultation.ConsultationResponse;
import com.app.emsx.services.ConsultationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/consultations")
@RequiredArgsConstructor
public class ConsultationController {

    private final ConsultationService service;

    @PostMapping
    public ResponseEntity<ApiResponse<ConsultationResponse>> create(@Valid @RequestBody ConsultationRequest request) {
        ConsultationResponse created = service.create(request);
        return ResponseEntity.ok(ApiResponse.ok("Consulta creada correctamente", created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ConsultationResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody ConsultationRequest request
    ) {
        ConsultationResponse updated = service.update(id, request);
        return ResponseEntity.ok(ApiResponse.ok("Consulta actualizada correctamente", updated));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ConsultationResponse>>> findAll() {
        return ResponseEntity.ok(ApiResponse.ok("Lista de consultas", service.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ConsultationResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok("Consulta encontrada", service.findById(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("Consulta eliminada correctamente", null));
    }
}
