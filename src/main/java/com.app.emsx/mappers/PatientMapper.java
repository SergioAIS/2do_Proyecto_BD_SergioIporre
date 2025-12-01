package com.app.emsx.mappers;

import com.app.emsx.dtos.patient.PatientRequest;
import com.app.emsx.dtos.patient.PatientResponse;
import com.app.emsx.entities.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDate;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PatientMapper {

    // Convierte un PatientRequest en una entidad Patient
    // Incluye el parseo de la fecha de nacimiento de String a LocalDate
    default Patient toEntity(PatientRequest dto) {
        if (dto == null) return null;
        Patient entity = new Patient();
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setAddress(dto.getAddress());
        if (dto.getDateOfBirth() != null && !dto.getDateOfBirth().isBlank()) {
            entity.setDateOfBirth(LocalDate.parse(dto.getDateOfBirth()));
        }
        return entity;
    }

    // Convierte una entidad Patient en PatientResponse
    // La fecha de nacimiento se convierte de LocalDate a String
    default PatientResponse toResponse(Patient entity) {
        if (entity == null) return null;
        PatientResponse dto = new PatientResponse();
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());
        dto.setAddress(entity.getAddress());
        dto.setDateOfBirth(entity.getDateOfBirth() != null ? entity.getDateOfBirth().toString() : null);
        return dto;
    }

    // Actualiza una entidad Patient existente con los campos no nulos del DTO
    // Si viene una fecha de nacimiento en formato String, se vuelve a parsear a LocalDate
    default void updateEntityFromRequest(PatientRequest dto, Patient entity) {
        if (dto == null || entity == null) return;
        if (dto.getFirstName() != null) entity.setFirstName(dto.getFirstName());
        if (dto.getLastName() != null) entity.setLastName(dto.getLastName());
        if (dto.getEmail() != null) entity.setEmail(dto.getEmail());
        if (dto.getPhone() != null) entity.setPhone(dto.getPhone());
        if (dto.getAddress() != null) entity.setAddress(dto.getAddress());
        if (dto.getDateOfBirth() != null && !dto.getDateOfBirth().isBlank()) {
            entity.setDateOfBirth(LocalDate.parse(dto.getDateOfBirth()));
        }
    }
}
