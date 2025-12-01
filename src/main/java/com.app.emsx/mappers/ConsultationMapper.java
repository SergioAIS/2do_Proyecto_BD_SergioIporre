package com.app.emsx.mappers;

import com.app.emsx.dtos.consultation.ConsultationRequest;
import com.app.emsx.dtos.consultation.ConsultationResponse;
import com.app.emsx.entities.Appointment;
import com.app.emsx.entities.Consultation;
import com.app.emsx.entities.MedicalRecord;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ConsultationMapper {

    // Convierte un ConsultationRequest en una entidad Consultation
    // Asocia cita e historia médica y copia las notas
    default Consultation toEntity(ConsultationRequest dto, Appointment appointment, MedicalRecord medicalRecord) {
        if (dto == null) return null;
        Consultation entity = new Consultation();
        entity.setAppointment(appointment);
        entity.setMedicalRecord(medicalRecord);
        entity.setNotes(dto.getNotes());
        return entity;
    }

    // Convierte una entidad Consultation en ConsultationResponse
    // Incluye ids de la cita y la historia médica asociadas
    default ConsultationResponse toResponse(Consultation entity) {
        if (entity == null) return null;
        ConsultationResponse dto = new ConsultationResponse();
        dto.setId(entity.getId());
        if (entity.getAppointment() != null) {
            dto.setAppointmentId(entity.getAppointment().getId());
        }
        if (entity.getMedicalRecord() != null) {
            dto.setMedicalRecordId(entity.getMedicalRecord().getId());
        }
        dto.setNotes(entity.getNotes());
        return dto;
    }

    // Actualiza una consulta existente con los campos no nulos del DTO
    // Permite cambiar la cita o la historia médica asociadas
    default void updateEntityFromRequest(ConsultationRequest dto, Consultation entity, Appointment appointment, MedicalRecord medicalRecord) {
        if (dto == null || entity == null) return;
        if (appointment != null) entity.setAppointment(appointment);
        if (medicalRecord != null) entity.setMedicalRecord(medicalRecord);
        if (dto.getNotes() != null) entity.setNotes(dto.getNotes());
    }
}
