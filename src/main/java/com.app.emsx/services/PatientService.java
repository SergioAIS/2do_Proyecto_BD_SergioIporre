package com.app.emsx.services;

import com.app.emsx.dtos.patient.PatientRequest;
import com.app.emsx.dtos.patient.PatientResponse;

import java.util.List;

public interface PatientService {
    PatientResponse create(PatientRequest request);
    PatientResponse update(Long id, PatientRequest request);
    void delete(Long id);
    PatientResponse findById(Long id);
    List<PatientResponse> findAll();
}
