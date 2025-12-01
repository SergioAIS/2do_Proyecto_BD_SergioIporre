package com.app.emsx.controllers;

import com.app.emsx.common.ApiResponse;
import com.app.emsx.dtos.medicalrecord.MedicalRecordRequest;
import com.app.emsx.dtos.medicalrecord.MedicalRecordResponse;
import com.app.emsx.services.MedicalRecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medical-records")
@RequiredArgsConstructor
public class MedicalRecordController {

    private final MedicalRecordService service;

    @PostMapping
    public ResponseEntity<ApiResponse<MedicalRecordResponse>> create(@Valid @RequestBody MedicalRecordRequest request) {
        MedicalRecordResponse created = service.create(request);
        return ResponseEntity.ok(ApiResponse.ok("Historia médica creada correctamente", created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<MedicalRecordResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody MedicalRecordRequest request
    ) {
        MedicalRecordResponse updated = service.update(id, request);
        return ResponseEntity.ok(ApiResponse.ok("Historia médica actualizada correctamente", updated));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<MedicalRecordResponse>>> findAll() {
        return ResponseEntity.ok(ApiResponse.ok("Lista de historias médicas", service.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MedicalRecordResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok("Historia médica encontrada", service.findById(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("Historia médica eliminada correctamente", null));
    }
}
