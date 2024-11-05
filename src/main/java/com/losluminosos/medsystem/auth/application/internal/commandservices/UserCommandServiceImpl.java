package com.losluminosos.medsystem.auth.application.internal.commandservices;

import com.losluminosos.medsystem.auth.domain.model.aggregates.User;
import com.losluminosos.medsystem.auth.domain.model.commands.CreateUserCommand;
import com.losluminosos.medsystem.auth.domain.model.valueobjects.Roles;
import com.losluminosos.medsystem.auth.domain.services.UserCommandService;
import com.losluminosos.medsystem.auth.infrastructure.persistence.jpa.repositories.RoleRepository;
import com.losluminosos.medsystem.auth.infrastructure.persistence.jpa.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserCommandServiceImpl implements UserCommandService {

    final private UserRepository userRepository;
    final private RoleRepository roleRepository;

    public UserCommandServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public Optional<User> handle(CreateUserCommand command) {
        if (userRepository.existsByUsername(command.username()))
            throw new IllegalArgumentException("Username already exists");
        var role = Roles.valueOf(command.role());
        if (!roleRepository.existsByName(role))
            throw new IllegalArgumentException("Role does not exist");
        var user = new User(command.username(), command.password(), roleRepository.findByName(role).get());
        userRepository.save(user);
        return Optional.of(user);
    }
}
