package com.losluminosos.medsystem.auth.application.internal.queryservices;

import com.losluminosos.medsystem.auth.domain.model.aggregates.User;
import com.losluminosos.medsystem.auth.domain.model.entities.Role;
import com.losluminosos.medsystem.auth.domain.model.queries.*;
import com.losluminosos.medsystem.auth.domain.model.valueobjects.Roles;
import com.losluminosos.medsystem.auth.domain.services.UserQueryService;
import com.losluminosos.medsystem.auth.infrastructure.persistence.jpa.repositories.RoleRepository;
import com.losluminosos.medsystem.auth.infrastructure.persistence.jpa.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserQueryServiceImpl implements UserQueryService {
    final UserRepository userRepository;
    final RoleRepository roleRepository;

    public UserQueryServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<User> handle(GetAllUsersQuery query) {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> handle(GetUserByUsernameQuery query) {
        return userRepository.findByUsername(query.username());
    }

    @Override
    public Optional<User> handle(GetUserByIdQuery query) {
        return userRepository.findById(query.id());
    }

    @Override
    public Optional<User> handle(GetUserByUsernameAndPassword query) {
        return userRepository.findByUsernameAndPassword(query.username(), query.password());
    }

    @Override
    public List<User> handle(GetUsersByRole query) {
        var role = Roles.valueOf(query.role());
        return userRepository.findUsersByRole(roleRepository.findByName(role).get());
    }
}
