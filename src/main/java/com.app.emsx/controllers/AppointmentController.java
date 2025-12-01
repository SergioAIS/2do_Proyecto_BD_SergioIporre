package com.app.emsx.controllers;

import com.app.emsx.common.ApiResponse;
import com.app.emsx.dtos.appointment.AppointmentRequest;
import com.app.emsx.dtos.appointment.AppointmentResponse;
import com.app.emsx.services.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService service;

    @PostMapping
    public ResponseEntity<ApiResponse<AppointmentResponse>> create(@Valid @RequestBody AppointmentRequest request) {
        AppointmentResponse created = service.create(request);
        return ResponseEntity.ok(ApiResponse.ok("Cita creada correctamente", created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AppointmentResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody AppointmentRequest request
    ) {
        AppointmentResponse updated = service.update(id, request);
        return ResponseEntity.ok(ApiResponse.ok("Cita actualizada correctamente", updated));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AppointmentResponse>>> findAll() {
        return ResponseEntity.ok(ApiResponse.ok("Lista de citas", service.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AppointmentResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok("Cita encontrada", service.findById(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.ok("Cita eliminada correctamente", null));
    }
}
