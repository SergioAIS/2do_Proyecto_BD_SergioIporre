package com.app.emsx.services;

import com.app.emsx.dtos.doctor.DoctorRequest;
import com.app.emsx.dtos.doctor.DoctorResponse;

import java.util.List;

public interface DoctorService {
    DoctorResponse create(DoctorRequest request);
    DoctorResponse update(Long id, DoctorRequest request);
    void delete(Long id);
    DoctorResponse findById(Long id);
    List<DoctorResponse> findAll();
}
