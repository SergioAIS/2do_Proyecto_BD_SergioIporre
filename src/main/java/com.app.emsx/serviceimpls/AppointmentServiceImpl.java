package com.app.emsx.serviceimpls;

import com.app.emsx.dtos.appointment.AppointmentRequest;
import com.app.emsx.dtos.appointment.AppointmentResponse;
import com.app.emsx.entities.Appointment;
import com.app.emsx.entities.Doctor;
import com.app.emsx.entities.Patient;
import com.app.emsx.exceptions.BusinessRuleException;
import com.app.emsx.exceptions.ResourceNotFoundException;
import com.app.emsx.mappers.AppointmentMapper;
import com.app.emsx.repositories.AppointmentRepository;
import com.app.emsx.repositories.DoctorRepository;
import com.app.emsx.repositories.PatientRepository;
import com.app.emsx.services.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository repository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final AppointmentMapper mapper;

    @Override
    public AppointmentResponse create(AppointmentRequest request) {
        // Validar que la fecha de la cita no sea pasada y tenga formato válido
        if (request.getDate() != null && !request.getDate().trim().isEmpty()) {
            try {
                LocalDate date = LocalDate.parse(request.getDate().trim());
                LocalDate today = LocalDate.now();
                if (date.isBefore(today)) {
                    throw new BusinessRuleException("La fecha de la cita no puede ser en el pasado");
                }
            } catch (Exception e) {
                if (e instanceof BusinessRuleException) {
                    throw e;
                }
                throw new BusinessRuleException("Formato de fecha inválido: " + request.getDate());
            }
        }
        // Validar que la hora tenga formato válido
        if (request.getTime() != null && !request.getTime().trim().isEmpty()) {
            try {
                LocalTime.parse(request.getTime().trim());
            } catch (Exception e) {
                throw new BusinessRuleException("Formato de hora inválido: " + request.getTime());
            }
        }
        // Validar que el estado de la cita sea uno de los permitidos
        if (request.getStatus() != null) {
            String status = request.getStatus().trim().toUpperCase();
            if (!(status.equals("SCHEDULED") || status.equals("COMPLETED") || status.equals("CANCELLED"))) {
                throw new BusinessRuleException("El estado de la cita debe ser SCHEDULED, COMPLETED o CANCELLED");
            }
        }
        // Verificar que el paciente exista
        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado con ID: " + request.getPatientId()));
        // Verificar que el doctor exista
        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor no encontrado con ID: " + request.getDoctorId()));
        Appointment appointment = mapper.toEntity(request, patient, doctor);
        return mapper.toResponse(repository.save(appointment));
    }

    @Override
    public AppointmentResponse update(Long id, AppointmentRequest request) {
        Appointment appointment = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada con ID: " + id));
        // Validar fecha de la cita al actualizar
        if (request.getDate() != null && !request.getDate().trim().isEmpty()) {
            try {
                LocalDate date = LocalDate.parse(request.getDate().trim());
                LocalDate today = LocalDate.now();
                if (date.isBefore(today)) {
                    throw new BusinessRuleException("La fecha de la cita no puede ser en el pasado");
                }
            } catch (Exception e) {
                if (e instanceof BusinessRuleException) {
                    throw e;
                }
                throw new BusinessRuleException("Formato de fecha inválido: " + request.getDate());
            }
        }
        if (request.getTime() != null && !request.getTime().trim().isEmpty()) {
            try {
                LocalTime.parse(request.getTime().trim());
            } catch (Exception e) {
                throw new BusinessRuleException("Formato de hora inválido: " + request.getTime());
            }
        }
        if (request.getStatus() != null) {
            String status = request.getStatus().trim().toUpperCase();
            if (!(status.equals("SCHEDULED") || status.equals("COMPLETED") || status.equals("CANCELLED"))) {
                throw new BusinessRuleException("El estado de la cita debe ser SCHEDULED, COMPLETED o CANCELLED");
            }
        }
        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado con ID: " + request.getPatientId()));
        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Doctor no encontrado con ID: " + request.getDoctorId()));

        mapper.updateEntityFromRequest(request, appointment, patient, doctor);
        return mapper.toResponse(repository.save(appointment));
    }

    @Override
    public void delete(Long id) {
        Appointment appointment = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada con ID: " + id));
        repository.delete(appointment);
    }

    @Override
    public AppointmentResponse findById(Long id) {
        Appointment appointment = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada con ID: " + id));
        return mapper.toResponse(appointment);
    }

    @Override
    public List<AppointmentResponse> findAll() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }
}
