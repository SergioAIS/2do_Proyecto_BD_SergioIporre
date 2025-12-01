package com.app.emsx.dtos.medicalrecord;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicalRecordResponse {

    private Long id;

    private Long patientId;
    private String patientName;

    private String diagnosis;
    private String treatment;
    private String notes;
}
