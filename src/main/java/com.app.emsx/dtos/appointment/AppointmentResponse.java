package com.app.emsx.dtos.appointment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentResponse {

    private Long id;

    private Long patientId;
    private String patientName;

    private Long doctorId;
    private String doctorName;

    private String date;
    private String time;
    private String reason;
    private String status;
}
