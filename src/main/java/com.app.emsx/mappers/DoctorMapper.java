package com.app.emsx.mappers;

import com.app.emsx.dtos.doctor.DoctorRequest;
import com.app.emsx.dtos.doctor.DoctorResponse;
import com.app.emsx.entities.Doctor;
import com.app.emsx.entities.Specialty;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DoctorMapper {

    // Convierte un DoctorRequest en una entidad Doctor
    // Recibe la entidad Specialty ya cargada para asignarla directamente al doctor
    default Doctor toEntity(DoctorRequest dto, Specialty specialty) {
        if (dto == null) return null;
        Doctor entity = new Doctor();
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setSpecialty(specialty);
        return entity;
    }

    // Convierte una entidad Doctor en DoctorResponse
    // Incluye los datos básicos de la especialidad (id y nombre) si existen
    default DoctorResponse toResponse(Doctor entity) {
        if (entity == null) return null;
        DoctorResponse dto = new DoctorResponse();
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());
        if (entity.getSpecialty() != null) {
            dto.setSpecialtyId(entity.getSpecialty().getId());
            dto.setSpecialtyName(entity.getSpecialty().getName());
        }
        return dto;
    }

    // Actualiza un Doctor existente con los campos no nulos del DTO
    // Permite cambiar la especialidad si se envía una nueva entidad Specialty
    default void updateEntityFromRequest(DoctorRequest dto, Doctor entity, Specialty specialty) {
        if (dto == null || entity == null) return;
        if (dto.getFirstName() != null) entity.setFirstName(dto.getFirstName());
        if (dto.getLastName() != null) entity.setLastName(dto.getLastName());
        if (dto.getEmail() != null) entity.setEmail(dto.getEmail());
        if (dto.getPhone() != null) entity.setPhone(dto.getPhone());
        if (specialty != null) entity.setSpecialty(specialty);
    }
}
