package com.app.emsx.controllers;

import com.app.emsx.common.ApiResponse;
import com.app.emsx.dtos.doctor.DoctorRequest;
import com.app.emsx.dtos.doctor.DoctorResponse;
import com.app.emsx.services.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService service;

    @PostMapping
    public ResponseEntity<ApiResponse<DoctorResponse>> create(@Valid @RequestBody DoctorRequest request) {
        DoctorResponse created = service.create(request);
        return ResponseEntity.ok(ApiResponse.ok("Doctor creado correctamente", created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DoctorResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody DoctorRequest request
    ) {
        DoctorResponse updated = service.update(id, request);
        return ResponseEntity.ok(ApiResponse.ok("Doctor actualizado correctamente", updated));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<DoctorResponse>>> findAll() {
        return ResponseEntity.ok(ApiResponse.ok("Lista de doctores", service.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DoctorResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok("Doctor encontrado", service.findById(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("Doctor eliminado correctamente", null));
    }
}
