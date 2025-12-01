package com.app.emsx.serviceimpls;

import com.app.emsx.dtos.doctor.DoctorRequest;
import com.app.emsx.dtos.doctor.DoctorResponse;
import com.app.emsx.entities.Doctor;
import com.app.emsx.entities.Specialty;
import com.app.emsx.exceptions.BusinessRuleException;
import com.app.emsx.exceptions.ResourceNotFoundException;
import com.app.emsx.mappers.DoctorMapper;
import com.app.emsx.repositories.DoctorRepository;
import com.app.emsx.repositories.SpecialtyRepository;
import com.app.emsx.services.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository repository;
    private final SpecialtyRepository specialtyRepository;
    private final DoctorMapper mapper;

    @Override
    public DoctorResponse create(DoctorRequest request) {
        // Validar longitud mínima y máxima del nombre del doctor
        if (request.getFirstName() != null) {
            if (request.getFirstName().trim().length() < 2) {
                throw new BusinessRuleException("El nombre debe tener mínimo 2 letras");
            }
            if (request.getFirstName().length() > 25) {
                throw new BusinessRuleException("El nombre no puede tener más de 25 letras");
            }
        }
        // Validar longitud máxima del apellido del doctor
        if (request.getLastName() != null && request.getLastName().length() > 20) {
            throw new BusinessRuleException("El apellido no puede tener más de 20 letras");
        }
        // Validar que el teléfono del doctor tenga exactamente 8 dígitos
        if (request.getPhone() != null && request.getPhone().length() != 8) {
            throw new BusinessRuleException("El teléfono debe tener exactamente 8 dígitos");
        }

        // Validar formato del email del doctor (igual que en Patient)
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

        // Validar que no exista otro doctor con el mismo email
        if (repository.existsByEmail(request.getEmail())) {
            throw new BusinessRuleException("Ya existe un doctor con ese email");
        }
        // Validar que no exista otro doctor con el mismo teléfono
        if (request.getPhone() != null && repository.existsByPhone(request.getPhone())) {
            throw new BusinessRuleException("Ya existe un doctor con ese teléfono");
        }

        Specialty specialty = specialtyRepository.findById(request.getSpecialtyId())
                .orElseThrow(() -> new ResourceNotFoundException("Especialidad no encontrada con ID: " + request.getSpecialtyId()));

        Doctor doctor = mapper.toEntity(request, specialty);
        return mapper.toResponse(repository.save(doctor));
    }

    @Override
    public DoctorResponse update(Long id, DoctorRequest request) {
        Doctor doctor = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor no encontrado con ID: " + id));
        // Validar longitud mínima y máxima del nombre al actualizar
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

        // Validar email duplicado sólo si el email cambió
        if (request.getEmail() != null && !request.getEmail().equals(doctor.getEmail()) &&
                repository.existsByEmail(request.getEmail())) {
            throw new BusinessRuleException("Ya existe un doctor con ese email");
        }
        // Validar teléfono duplicado sólo si el teléfono cambió
        if (request.getPhone() != null && !request.getPhone().equals(doctor.getPhone()) &&
                repository.existsByPhone(request.getPhone())) {
            throw new BusinessRuleException("Ya existe un doctor con ese teléfono");
        }

        Specialty specialty = specialtyRepository.findById(request.getSpecialtyId())
                .orElseThrow(() -> new ResourceNotFoundException("Especialidad no encontrada con ID: " + request.getSpecialtyId()));

        mapper.updateEntityFromRequest(request, doctor, specialty);
        return mapper.toResponse(repository.save(doctor));
    }

    @Override
    public void delete(Long id) {
        Doctor doctor = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor no encontrado con ID: " + id));
        repository.delete(doctor);
    }

    @Override
    public DoctorResponse findById(Long id) {
        Doctor doctor = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor no encontrado con ID: " + id));
        return mapper.toResponse(doctor);
    }

    @Override
    public List<DoctorResponse> findAll() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }
}
