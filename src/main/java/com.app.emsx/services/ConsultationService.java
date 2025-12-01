package com.app.emsx.services;

import com.app.emsx.dtos.consultation.ConsultationRequest;
import com.app.emsx.dtos.consultation.ConsultationResponse;

import java.util.List;

public interface ConsultationService {
    ConsultationResponse create(ConsultationRequest request);
    ConsultationResponse update(Long id, ConsultationRequest request);
    void delete(Long id);
    ConsultationResponse findById(Long id);
    List<ConsultationResponse> findAll();
}
