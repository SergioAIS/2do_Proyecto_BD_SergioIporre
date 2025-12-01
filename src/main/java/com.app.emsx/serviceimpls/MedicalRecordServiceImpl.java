package com.app.emsx.serviceimpls;

import com.app.emsx.dtos.medicalrecord.MedicalRecordRequest;
import com.app.emsx.dtos.medicalrecord.MedicalRecordResponse;
import com.app.emsx.entities.MedicalRecord;
import com.app.emsx.entities.Patient;
import com.app.emsx.exceptions.BusinessRuleException;
import com.app.emsx.exceptions.ResourceNotFoundException;
import com.app.emsx.mappers.MedicalRecordMapper;
import com.app.emsx.repositories.MedicalRecordRepository;
import com.app.emsx.repositories.PatientRepository;
import com.app.emsx.services.MedicalRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicalRecordServiceImpl implements MedicalRecordService {

    private final MedicalRecordRepository repository;
    private final PatientRepository patientRepository;
    private final MedicalRecordMapper mapper;

    @Override
    public MedicalRecordResponse create(MedicalRecordRequest request) {
        // Validar longitud máxima del diagnóstico
        if (request.getDiagnosis() != null && request.getDiagnosis().length() > 255) {
            throw new BusinessRuleException("El diagnóstico no puede tener más de 255 caracteres");
        }
        // Validar longitud máxima del tratamiento
        if (request.getTreatment() != null && request.getTreatment().length() > 255) {
            throw new BusinessRuleException("El tratamiento no puede tener más de 255 caracteres");
        }
        // Validar longitud máxima de las notas
        if (request.getNotes() != null && request.getNotes().length() > 500) {
            throw new BusinessRuleException("Las notas no pueden tener más de 500 caracteres");
        }
        // Verificar que el paciente asociado exista
        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado con ID: " + request.getPatientId()));
        MedicalRecord record = mapper.toEntity(request, patient);
        return mapper.toResponse(repository.save(record));
    }

    @Override
    public MedicalRecordResponse update(Long id, MedicalRecordRequest request) {
        MedicalRecord record = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Historia médica no encontrada con ID: " + id));
        // Validar longitud máxima del diagnóstico al actualizar
        if (request.getDiagnosis() != null && request.getDiagnosis().length() > 255) {
            throw new BusinessRuleException("El diagnóstico no puede tener más de 255 caracteres");
        }
        // Validar longitud máxima del tratamiento al actualizar
        if (request.getTreatment() != null && request.getTreatment().length() > 255) {
            throw new BusinessRuleException("El tratamiento no puede tener más de 255 caracteres");
        }
        // Validar longitud máxima de las notas al actualizar
        if (request.getNotes() != null && request.getNotes().length() > 500) {
            throw new BusinessRuleException("Las notas no pueden tener más de 500 caracteres");
        }
        // Verificar que el paciente asociado exista al actualizar
        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado con ID: " + request.getPatientId()));

        mapper.updateEntityFromRequest(request, record, patient);
        return mapper.toResponse(repository.save(record));
    }

    @Override
    public void delete(Long id) {
        MedicalRecord record = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Historia médica no encontrada con ID: " + id));
        repository.delete(record);
    }

    @Override
    public MedicalRecordResponse findById(Long id) {
        MedicalRecord record = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Historia médica no encontrada con ID: " + id));
        return mapper.toResponse(record);
    }

    @Override
    public List<MedicalRecordResponse> findAll() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }
}
