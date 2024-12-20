package com.losluminosos.medsystem.profiles.infrastructure.persistence.jpa.repositories;

import com.losluminosos.medsystem.profiles.domain.model.aggregates.Doctor;
import com.losluminosos.medsystem.profiles.domain.model.aggregates.Patient;
import com.losluminosos.medsystem.profiles.domain.model.valueobjects.EmailAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    boolean existsByEmail(EmailAddress emailAddress);
    boolean existsByUid(String uid);
    Optional<Patient> findByUid(String uid);
}
