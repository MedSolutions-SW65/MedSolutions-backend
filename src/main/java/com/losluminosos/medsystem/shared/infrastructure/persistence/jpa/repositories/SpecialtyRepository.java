package com.losluminosos.medsystem.shared.infrastructure.persistence.jpa.repositories;

import com.losluminosos.medsystem.shared.domain.model.entities.Specialty;
import com.losluminosos.medsystem.shared.domain.model.valueobjects.Specialties;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpecialtyRepository extends JpaRepository<Specialty, Long> {
    boolean existsByName(Specialties name);
    Optional<Specialty> findByName(Specialties name);
}
