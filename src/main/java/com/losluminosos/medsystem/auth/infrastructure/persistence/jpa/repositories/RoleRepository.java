package com.losluminosos.medsystem.auth.infrastructure.persistence.jpa.repositories;

import com.losluminosos.medsystem.auth.domain.model.entities.Role;
import com.losluminosos.medsystem.auth.domain.model.valueobjects.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(Roles name);
    boolean existsByName(Roles name);
}
