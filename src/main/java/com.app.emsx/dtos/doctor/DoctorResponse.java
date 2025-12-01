package com.app.emsx.dtos.doctor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;

    private Long specialtyId;
    private String specialtyName;
}
