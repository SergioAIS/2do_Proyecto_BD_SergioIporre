package com.app.emsx.dtos.medicalrecord;

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
public class MedicalRecordRequest {

    @NotNull(message = "El paciente es obligatorio")
    private Long patientId;

    @NotBlank(message = "El diagnóstico es obligatorio")
    private String diagnosis;

    private String treatment;
    private String notes;
}
