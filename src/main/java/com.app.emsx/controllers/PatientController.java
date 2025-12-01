package com.app.emsx.controllers;

import com.app.emsx.common.ApiResponse;
import com.app.emsx.dtos.patient.PatientRequest;
import com.app.emsx.dtos.patient.PatientResponse;
import com.app.emsx.services.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService service;

    @PostMapping
    public ResponseEntity<ApiResponse<PatientResponse>> create(@Valid @RequestBody PatientRequest request) {
        PatientResponse created = service.create(request);
        return ResponseEntity.ok(ApiResponse.ok("Paciente creado correctamente", created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PatientResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody PatientRequest request
    ) {
        PatientResponse updated = service.update(id, request);
        return ResponseEntity.ok(ApiResponse.ok("Paciente actualizado correctamente", updated));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PatientResponse>>> findAll() {
        return ResponseEntity.ok(ApiResponse.ok("Lista de pacientes", service.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PatientResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok("Paciente encontrado", service.findById(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("Paciente eliminado correctamente", null));
    }
}
