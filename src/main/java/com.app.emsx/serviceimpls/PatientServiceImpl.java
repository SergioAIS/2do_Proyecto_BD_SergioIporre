package com.app.emsx.serviceimpls;

import com.app.emsx.dtos.patient.PatientRequest;
import com.app.emsx.dtos.patient.PatientResponse;
import com.app.emsx.entities.Patient;
import com.app.emsx.exceptions.BusinessRuleException;
import com.app.emsx.exceptions.ResourceNotFoundException;
import com.app.emsx.mappers.PatientMapper;
import com.app.emsx.repositories.PatientRepository;
import com.app.emsx.services.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository repository;
    private final PatientMapper mapper;

    @Override
    public PatientResponse create(PatientRequest request) {
        // Validar longitud mínima y máxima del nombre
        if (request.getFirstName() != null) {
            if (request.getFirstName().trim().length() < 2) {
                throw new BusinessRuleException("El nombre debe tener mínimo 2 letras");
            }
            if (request.getFirstName().length() > 25) {
                throw new BusinessRuleException("El nombre no puede tener más de 25 letras");
            }
        }
        // Validar longitud máxima del apellido
        if (request.getLastName() != null && request.getLastName().length() > 20) {
            throw new BusinessRuleException("El apellido no puede tener más de 20 letras");
        }
        // Validar que el teléfono tenga exactamente 8 dígitos
        if (request.getPhone() != null && request.getPhone().length() != 8) {
            throw new BusinessRuleException("El teléfono debe tener exactamente 8 dígitos");
        }

        // Validar email: formato básico, parte local 4-30 caracteres, dominio y extensión con longitudes máximas
        if (request.getEmail() != null) {
            String email = request.getEmail().trim();
            if (!email.matches("^[a-zA-Z0-9._]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
                throw new BusinessRuleException("El email debe contener solo letras, números, puntos y guiones bajos");
            }
            String[] parts = email.split("@");
            if (parts.length == 2) {
                String localPart = parts[0];
                String domainPart = parts[1];
                String[] domainParts = domainPart.split("\\.");

                // Validar parte local (antes del @)
                if (localPart.length() < 4) {
                    throw new BusinessRuleException("La parte antes del @ debe tener mínimo 4 caracteres");
                }
                if (localPart.length() > 30) {
                    throw new BusinessRuleException("La parte antes del @ debe tener máximo 30 caracteres");
                }

                // Validar dominio y extensión (después del @)
                if (domainParts.length >= 2) {
                    // Dominio (después del @ y antes del último punto)
                    String domain = String.join(".", java.util.Arrays.copyOf(domainParts, domainParts.length - 1));
                    if (domain.length() > 20) {
                        throw new BusinessRuleException("El dominio (después del @) debe tener máximo 20 caracteres");
                    }

                    // Extensión (después del último punto)
                    String extension = domainParts[domainParts.length - 1];
                    if (extension.length() > 20) {
                        throw new BusinessRuleException("La extensión (después del último punto) debe tener máximo 20 caracteres");
                    }
                }
            }
        }

        // Validar fecha de nacimiento: rango razonable y no futura
        if (request.getDateOfBirth() != null && !request.getDateOfBirth().trim().isEmpty()) {
            try {
                LocalDate dob = LocalDate.parse(request.getDateOfBirth().trim());
                LocalDate minDate = LocalDate.of(1900, 1, 1);
                LocalDate today = LocalDate.now();
                if (dob.isBefore(minDate)) {
                    throw new BusinessRuleException("La fecha de nacimiento no puede ser anterior a 1900-01-01");
                }
                if (dob.isAfter(today)) {
                    throw new BusinessRuleException("La fecha de nacimiento no puede ser futura");
                }
            } catch (Exception e) {
                if (e instanceof BusinessRuleException) {
                    throw e;
                }
                throw new BusinessRuleException("Formato de fecha de nacimiento inválido: " + request.getDateOfBirth());
            }
        }

        // Validaciones de unicidad de email y teléfono
        if (repository.existsByEmail(request.getEmail())) {
            throw new BusinessRuleException("Ya existe un paciente con ese email");
        }
        if (request.getPhone() != null && repository.existsByPhone(request.getPhone())) {
            throw new BusinessRuleException("Ya existe un paciente con ese teléfono");
        }
        Patient patient = mapper.toEntity(request);
        return mapper.toResponse(repository.save(patient));
    }

    @Override
    public PatientResponse update(Long id, PatientRequest request) {
        Patient patient = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado con ID: " + id));
        // Validar longitud mínima y máxima del nombre
        if (request.getFirstName() != null) {
            if (request.getFirstName().trim().length() < 2) {
                throw new BusinessRuleException("El nombre debe tener mínimo 2 letras");
            }
            if (request.getFirstName().length() > 25) {
                throw new BusinessRuleException("El nombre no puede tener más de 25 letras");
            }
        }
        if (request.getLastName() != null && request.getLastName().length() > 20) {
            throw new BusinessRuleException("El apellido no puede tener más de 20 letras");
        }
        if (request.getPhone() != null && request.getPhone().length() != 8) {
            throw new BusinessRuleException("El teléfono debe tener exactamente 8 dígitos");
        }

        if (request.getEmail() != null) {
            String email = request.getEmail().trim();
            if (!email.matches("^[a-zA-Z0-9._]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
                throw new BusinessRuleException("El email debe contener solo letras, números, puntos y guiones bajos");
            }
            String[] parts = email.split("@");
            if (parts.length == 2) {
                String localPart = parts[0];
                String domainPart = parts[1];
                String[] domainParts = domainPart.split("\\.");

                if (localPart.length() < 4) {
                    throw new BusinessRuleException("La parte antes del @ debe tener mínimo 4 caracteres");
                }
                if (localPart.length() > 30) {
                    throw new BusinessRuleException("La parte antes del @ debe tener máximo 30 caracteres");
                }

                if (domainParts.length >= 2) {
                    String domain = String.join(".", java.util.Arrays.copyOf(domainParts, domainParts.length - 1));
                    if (domain.length() > 20) {
                        throw new BusinessRuleException("El dominio (después del @) debe tener máximo 20 caracteres");
                    }

                    String extension = domainParts[domainParts.length - 1];
                    if (extension.length() > 20) {
                        throw new BusinessRuleException("La extensión (después del último punto) debe tener máximo 20 caracteres");
                    }
                }
            }
        }

        if (request.getDateOfBirth() != null && !request.getDateOfBirth().trim().isEmpty()) {
            try {
                LocalDate dob = LocalDate.parse(request.getDateOfBirth().trim());
                LocalDate minDate = LocalDate.of(1900, 1, 1);
                LocalDate today = LocalDate.now();
                if (dob.isBefore(minDate)) {
                    throw new BusinessRuleException("La fecha de nacimiento no puede ser anterior a 1900-01-01");
                }
                if (dob.isAfter(today)) {
                    throw new BusinessRuleException("La fecha de nacimiento no puede ser futura");
                }
            } catch (Exception e) {
                if (e instanceof BusinessRuleException) {
                    throw e;
                }
                throw new BusinessRuleException("Formato de fecha de nacimiento inválido: " + request.getDateOfBirth());
            }
        }

        // Validar email duplicado sólo si el email cambió
        if (request.getEmail() != null && !request.getEmail().equals(patient.getEmail()) &&
                repository.existsByEmail(request.getEmail())) {
            throw new BusinessRuleException("Ya existe un paciente con ese email");
        }
        // Validar teléfono duplicado sólo si el teléfono cambió
        if (request.getPhone() != null && !request.getPhone().equals(patient.getPhone()) &&
                repository.existsByPhone(request.getPhone())) {
            throw new BusinessRuleException("Ya existe un paciente con ese teléfono");
        }

        mapper.updateEntityFromRequest(request, patient);
        return mapper.toResponse(repository.save(patient));
    }

    @Override
    public void delete(Long id) {
        Patient patient = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado con ID: " + id));
        repository.delete(patient);
    }

    @Override
    public PatientResponse findById(Long id) {
        Patient patient = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente no encontrado con ID: " + id));
        return mapper.toResponse(patient);
    }

    @Override
    public List<PatientResponse> findAll() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }
}
