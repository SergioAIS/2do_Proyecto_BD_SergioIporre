package com.app.emsx.repositories;

import com.app.emsx.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
}
