package com.app.emsx.dtos.consultation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConsultationResponse {

    private Long id;

    private Long appointmentId;
    private Long medicalRecordId;

    private String notes;
}
