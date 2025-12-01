package com.app.emsx.services;

import com.app.emsx.dtos.appointment.AppointmentRequest;
import com.app.emsx.dtos.appointment.AppointmentResponse;

import java.util.List;

public interface AppointmentService {
    AppointmentResponse create(AppointmentRequest request);
    AppointmentResponse update(Long id, AppointmentRequest request);
    void delete(Long id);
    AppointmentResponse findById(Long id);
    List<AppointmentResponse> findAll();
}
