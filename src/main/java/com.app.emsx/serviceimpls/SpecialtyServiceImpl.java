package com.app.emsx.serviceimpls;

import com.app.emsx.dtos.specialty.SpecialtyRequest;
import com.app.emsx.dtos.specialty.SpecialtyResponse;
import com.app.emsx.entities.Specialty;
import com.app.emsx.exceptions.BusinessRuleException;
import com.app.emsx.exceptions.ResourceNotFoundException;
import com.app.emsx.mappers.SpecialtyMapper;
import com.app.emsx.repositories.SpecialtyRepository;
import com.app.emsx.services.SpecialtyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpecialtyServiceImpl implements SpecialtyService {

    private final SpecialtyRepository repository;
    private final SpecialtyMapper mapper;

    @Override
    public SpecialtyResponse create(SpecialtyRequest request) {
        // Validar longitud máxima del nombre de la especialidad
        if (request.getName() != null && request.getName().length() > 50) {
            throw new BusinessRuleException("El nombre de la especialidad no puede tener más de 50 caracteres");
        }
        // Validar longitud máxima de la descripción de la especialidad
        if (request.getDescription() != null && request.getDescription().length() > 255) {
            throw new BusinessRuleException("La descripción de la especialidad no puede tener más de 255 caracteres");
        }

        // Validar unicidad del nombre de la especialidad
        if (repository.existsByName(request.getName())) {
            throw new BusinessRuleException("Ya existe una especialidad con ese nombre");
        }
        Specialty entity = mapper.toEntity(request);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public SpecialtyResponse update(Long id, SpecialtyRequest request) {
        Specialty specialty = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Especialidad no encontrada con ID: " + id));
        // Validar longitud máxima del nombre al actualizar
        // Se verifica que el nombre de la especialidad no supere los 50 caracteres al actualizar
        if (request.getName() != null && request.getName().length() > 50) {
            throw new BusinessRuleException("El nombre de la especialidad no puede tener más de 50 caracteres");
        }
        // Validar longitud máxima de la descripción al actualizar
        // Se verifica que la descripción de la especialidad no supere los 255 caracteres al actualizar
        if (request.getDescription() != null && request.getDescription().length() > 255) {
            throw new BusinessRuleException("La descripción de la especialidad no puede tener más de 255 caracteres");
        }

        // Validar unicidad del nombre sólo si cambió
        // Se verifica que no exista una especialidad con el mismo nombre si se cambió el nombre
        if (request.getName() != null && !request.getName().equals(specialty.getName()) &&
                repository.existsByName(request.getName())) {
            throw new BusinessRuleException("Ya existe una especialidad con ese nombre");
        }

        mapper.updateEntityFromRequest(request, specialty);
        return mapper.toResponse(repository.save(specialty));
    }

    @Override
    public void delete(Long id) {
        Specialty specialty = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Especialidad no encontrada con ID: " + id));
        repository.delete(specialty);
    }

    @Override
    public SpecialtyResponse findById(Long id) {
        Specialty specialty = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Especialidad no encontrada con ID: " + id));
        return mapper.toResponse(specialty);
    }

    @Override
    public List<SpecialtyResponse> findAll() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }
}
