package com.app.emsx.dtos.consultation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConsultationRequest {

    @NotNull(message = "La cita es obligatoria")
    private Long appointmentId;

    @NotNull(message = "La historia médica es obligatoria")
    private Long medicalRecordId;

    @NotBlank(message = "Las notas son obligatorias")
    private String notes;
}
