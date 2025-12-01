package com.app.emsx.repositories;

import com.app.emsx.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
}
