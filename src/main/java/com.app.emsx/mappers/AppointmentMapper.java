package com.app.emsx.mappers;

import com.app.emsx.dtos.appointment.AppointmentRequest;
import com.app.emsx.dtos.appointment.AppointmentResponse;
import com.app.emsx.entities.Appointment;
import com.app.emsx.entities.Doctor;
import com.app.emsx.entities.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDate;
import java.time.LocalTime;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AppointmentMapper {

    // Convierte un AppointmentRequest en una entidad Appointment
    // Asigna las entidades Patient y Doctor ya cargadas y parsea fecha y hora
    default Appointment toEntity(AppointmentRequest dto, Patient patient, Doctor doctor) {
        if (dto == null) return null;
        Appointment entity = new Appointment();
        entity.setPatient(patient);
        entity.setDoctor(doctor);
        if (dto.getDate() != null && !dto.getDate().isBlank()) {
            entity.setDate(LocalDate.parse(dto.getDate()));
        }
        if (dto.getTime() != null && !dto.getTime().isBlank()) {
            entity.setTime(LocalTime.parse(dto.getTime()));
        }
        entity.setReason(dto.getReason());
        entity.setStatus(dto.getStatus());
        return entity;
    }

    // Convierte una entidad Appointment en AppointmentResponse
    // Incluye ids y nombres de paciente y doctor, y convierte fecha/hora a String
    default AppointmentResponse toResponse(Appointment entity) {
        if (entity == null) return null;
        AppointmentResponse dto = new AppointmentResponse();
        dto.setId(entity.getId());
        if (entity.getPatient() != null) {
            dto.setPatientId(entity.getPatient().getId());
            dto.setPatientName(entity.getPatient().getFirstName() + " " + entity.getPatient().getLastName());
        }
        if (entity.getDoctor() != null) {
            dto.setDoctorId(entity.getDoctor().getId());
            dto.setDoctorName("Dr. " + entity.getDoctor().getFirstName() + " " + entity.getDoctor().getLastName());
        }
        dto.setDate(entity.getDate() != null ? entity.getDate().toString() : null);
        dto.setTime(entity.getTime() != null ? entity.getTime().toString() : null);
        dto.setReason(entity.getReason());
        dto.setStatus(entity.getStatus());
        return dto;
    }

    // Actualiza una cita existente con los campos no nulos del DTO
    // Permite cambiar paciente/doctor y volver a parsear fecha y hora si se envían
    default void updateEntityFromRequest(AppointmentRequest dto, Appointment entity, Patient patient, Doctor doctor) {
        if (dto == null || entity == null) return;
        if (patient != null) entity.setPatient(patient);
        if (doctor != null) entity.setDoctor(doctor);
        if (dto.getDate() != null && !dto.getDate().isBlank()) {
            entity.setDate(LocalDate.parse(dto.getDate()));
        }
        if (dto.getTime() != null && !dto.getTime().isBlank()) {
            entity.setTime(LocalTime.parse(dto.getTime()));
        }
        if (dto.getReason() != null) entity.setReason(dto.getReason());
        if (dto.getStatus() != null) entity.setStatus(dto.getStatus());
    }
}
