package com.app.emsx.mappers;

import com.app.emsx.dtos.medicalrecord.MedicalRecordRequest;
import com.app.emsx.dtos.medicalrecord.MedicalRecordResponse;
import com.app.emsx.entities.MedicalRecord;
import com.app.emsx.entities.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MedicalRecordMapper {

    // Convierte un MedicalRecordRequest en una entidad MedicalRecord
    // Asocia el Patient recibido y copia diagnóstico, tratamiento y notas
    default MedicalRecord toEntity(MedicalRecordRequest dto, Patient patient) {
        if (dto == null) return null;
        MedicalRecord entity = new MedicalRecord();
        entity.setPatient(patient);
        entity.setDiagnosis(dto.getDiagnosis());
        entity.setTreatment(dto.getTreatment());
        entity.setNotes(dto.getNotes());
        return entity;
    }

    // Convierte una entidad MedicalRecord en MedicalRecordResponse
    // Incluye id y nombre completo del paciente si está presente
    default MedicalRecordResponse toResponse(MedicalRecord entity) {
        if (entity == null) return null;
        MedicalRecordResponse dto = new MedicalRecordResponse();
        dto.setId(entity.getId());
        if (entity.getPatient() != null) {
            dto.setPatientId(entity.getPatient().getId());
            dto.setPatientName(entity.getPatient().getFirstName() + " " + entity.getPatient().getLastName());
        }
        dto.setDiagnosis(entity.getDiagnosis());
        dto.setTreatment(entity.getTreatment());
        dto.setNotes(entity.getNotes());
        return dto;
    }

    // Actualiza una historia médica existente con los campos no nulos del DTO
    // Permite cambiar el paciente asociado si se envía uno nuevo
    default void updateEntityFromRequest(MedicalRecordRequest dto, MedicalRecord entity, Patient patient) {
        if (dto == null || entity == null) return;
        if (patient != null) entity.setPatient(patient);
        if (dto.getDiagnosis() != null) entity.setDiagnosis(dto.getDiagnosis());
        if (dto.getTreatment() != null) entity.setTreatment(dto.getTreatment());
        if (dto.getNotes() != null) entity.setNotes(dto.getNotes());
    }
}
