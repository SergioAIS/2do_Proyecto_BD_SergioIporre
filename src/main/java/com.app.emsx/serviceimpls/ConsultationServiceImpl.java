package com.app.emsx.serviceimpls;

import com.app.emsx.dtos.consultation.ConsultationRequest;
import com.app.emsx.dtos.consultation.ConsultationResponse;
import com.app.emsx.entities.Appointment;
import com.app.emsx.entities.Consultation;
import com.app.emsx.entities.MedicalRecord;
import com.app.emsx.exceptions.BusinessRuleException;
import com.app.emsx.exceptions.ResourceNotFoundException;
import com.app.emsx.mappers.ConsultationMapper;
import com.app.emsx.repositories.AppointmentRepository;
import com.app.emsx.repositories.ConsultationRepository;
import com.app.emsx.repositories.MedicalRecordRepository;
import com.app.emsx.services.ConsultationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsultationServiceImpl implements ConsultationService {

    private final ConsultationRepository repository;
    private final AppointmentRepository appointmentRepository;
    private final MedicalRecordRepository medicalRecordRepository;
    private final ConsultationMapper mapper;

    @Override
    public ConsultationResponse create(ConsultationRequest request) {
        // Validar longitud máxima de las notas de la consulta
        if (request.getNotes() != null && request.getNotes().length() > 500) {
            throw new BusinessRuleException("Las notas de la consulta no pueden tener más de 500 caracteres");
        }
        // Verificar que la cita asociada exista
        Appointment appointment = appointmentRepository.findById(request.getAppointmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada con ID: " + request.getAppointmentId()));
        // Verificar que la historia médica asociada exista
        MedicalRecord record = medicalRecordRepository.findById(request.getMedicalRecordId())
                .orElseThrow(() -> new ResourceNotFoundException("Historia médica no encontrada con ID: " + request.getMedicalRecordId()));
        Consultation consultation = mapper.toEntity(request, appointment, record);
        return mapper.toResponse(repository.save(consultation));
    }

    @Override
    public ConsultationResponse update(Long id, ConsultationRequest request) {
        Consultation consultation = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consulta no encontrada con ID: " + id));
        // Validar longitud máxima de las notas al actualizar
        if (request.getNotes() != null && request.getNotes().length() > 500) {
            throw new BusinessRuleException("Las notas de la consulta no pueden tener más de 500 caracteres");
        }
        // Verificar que la cita asociada exista al actualizar
        Appointment appointment = appointmentRepository.findById(request.getAppointmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada con ID: " + request.getAppointmentId()));
        // Verificar que la historia médica asociada exista al actualizar
        MedicalRecord record = medicalRecordRepository.findById(request.getMedicalRecordId())
                .orElseThrow(() -> new ResourceNotFoundException("Historia médica no encontrada con ID: " + request.getMedicalRecordId()));

        mapper.updateEntityFromRequest(request, consultation, appointment, record);
        return mapper.toResponse(repository.save(consultation));
    }

    @Override
    public void delete(Long id) {
        Consultation consultation = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consulta no encontrada con ID: " + id));
        repository.delete(consultation);
    }

    @Override
    public ConsultationResponse findById(Long id) {
        Consultation consultation = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consulta no encontrada con ID: " + id));
        return mapper.toResponse(consultation);
    }

    @Override
    public List<ConsultationResponse> findAll() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }
}
