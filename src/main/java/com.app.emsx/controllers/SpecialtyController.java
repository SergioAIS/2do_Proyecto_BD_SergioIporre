package com.app.emsx.controllers;

import com.app.emsx.common.ApiResponse;
import com.app.emsx.dtos.specialty.SpecialtyRequest;
import com.app.emsx.dtos.specialty.SpecialtyResponse;
import com.app.emsx.services.SpecialtyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/specialties")
@RequiredArgsConstructor
public class SpecialtyController {

    private final SpecialtyService service;

    @PostMapping
    public ResponseEntity<ApiResponse<SpecialtyResponse>> create(@Valid @RequestBody SpecialtyRequest request) {
        SpecialtyResponse created = service.create(request);
        return ResponseEntity.ok(ApiResponse.ok("Especialidad creada correctamente", created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SpecialtyResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody SpecialtyRequest request
    ) {
        SpecialtyResponse updated = service.update(id, request);
        return ResponseEntity.ok(ApiResponse.ok("Especialidad actualizada correctamente", updated));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SpecialtyResponse>>> findAll() {
        return ResponseEntity.ok(ApiResponse.ok("Lista de especialidades", service.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SpecialtyResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok("Especialidad encontrada", service.findById(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("Especialidad eliminada correctamente", null));
    }
}
