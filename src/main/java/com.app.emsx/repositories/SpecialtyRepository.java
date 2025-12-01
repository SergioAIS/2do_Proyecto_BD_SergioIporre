package com.app.emsx.repositories;

import com.app.emsx.entities.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialtyRepository extends JpaRepository<Specialty, Long> {
    boolean existsByName(String name);
}
