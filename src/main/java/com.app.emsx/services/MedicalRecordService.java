package com.app.emsx.services;

import com.app.emsx.dtos.medicalrecord.MedicalRecordRequest;
import com.app.emsx.dtos.medicalrecord.MedicalRecordResponse;

import java.util.List;

public interface MedicalRecordService {
    MedicalRecordResponse create(MedicalRecordRequest request);
    MedicalRecordResponse update(Long id, MedicalRecordRequest request);
    void delete(Long id);
    MedicalRecordResponse findById(Long id);
    List<MedicalRecordResponse> findAll();
}
