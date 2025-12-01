package com.app.emsx.dtos.appointment;

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
public class AppointmentRequest {

    @NotNull(message = "El paciente es obligatorio")
    private Long patientId;

    @NotNull(message = "El doctor es obligatorio")
    private Long doctorId;

    // Se envían como strings ISO desde el frontend (yyyy-MM-dd y HH:mm)
    @NotBlank(message = "La fecha es obligatoria")
    private String date;

    @NotBlank(message = "La hora es obligatoria")
    private String time;

    private String reason;
    private String status;
}
