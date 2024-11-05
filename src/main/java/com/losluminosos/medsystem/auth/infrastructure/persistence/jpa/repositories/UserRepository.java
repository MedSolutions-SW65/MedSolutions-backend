package com.losluminosos.medsystem.auth.infrastructure.persistence.jpa.repositories;

import com.losluminosos.medsystem.auth.domain.model.aggregates.User;
import com.losluminosos.medsystem.auth.domain.model.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);
    Optional<User> findById(Long id);
    List<User> findUsersByRole(Role role);
    Optional<User> findByUsernameAndPassword(String username, String password);
}
