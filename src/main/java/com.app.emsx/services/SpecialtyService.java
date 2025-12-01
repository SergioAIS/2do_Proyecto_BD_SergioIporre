package com.app.emsx.services;

import com.app.emsx.dtos.specialty.SpecialtyRequest;
import com.app.emsx.dtos.specialty.SpecialtyResponse;

import java.util.List;

public interface SpecialtyService {
    SpecialtyResponse create(SpecialtyRequest request);
    SpecialtyResponse update(Long id, SpecialtyRequest request);
    void delete(Long id);
    SpecialtyResponse findById(Long id);
    List<SpecialtyResponse> findAll();
}
