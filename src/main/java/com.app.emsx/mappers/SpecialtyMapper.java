package com.app.emsx.mappers;

import com.app.emsx.dtos.specialty.SpecialtyRequest;
import com.app.emsx.dtos.specialty.SpecialtyResponse;
import com.app.emsx.entities.Specialty;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SpecialtyMapper {

    // Convierte un SpecialtyRequest en una entidad Specialty
    default Specialty toEntity(SpecialtyRequest dto) {
        if (dto == null) return null;
        Specialty entity = new Specialty();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        return entity;
    }

    // Convierte una entidad Specialty en SpecialtyResponse
    default SpecialtyResponse toResponse(Specialty entity) {
        if (entity == null) return null;
        SpecialtyResponse dto = new SpecialtyResponse();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        return dto;
    }

    // Actualiza una entidad Specialty existente con los campos no nulos del DTO
    default void updateEntityFromRequest(SpecialtyRequest dto, Specialty entity) {
        if (dto == null || entity == null) return;
        if (dto.getName() != null) entity.setName(dto.getName());
        if (dto.getDescription() != null) entity.setDescription(dto.getDescription());
    }
}
