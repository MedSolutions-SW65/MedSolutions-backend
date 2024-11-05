package com.losluminosos.medsystem.auth.application.internal.commandservices;

import com.losluminosos.medsystem.auth.domain.model.commands.SeedRolesCommand;
import com.losluminosos.medsystem.auth.domain.model.entities.Role;
import com.losluminosos.medsystem.auth.domain.model.valueobjects.Roles;
import com.losluminosos.medsystem.auth.domain.services.RoleCommandService;
import com.losluminosos.medsystem.auth.infrastructure.persistence.jpa.repositories.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class RoleCommandServiceImpl implements RoleCommandService {
    private final RoleRepository roleRepository;

    public RoleCommandServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void handle(SeedRolesCommand command) {
        Arrays.stream(Roles.values()).forEach(role -> {
            if (!roleRepository.existsByName(role)) {
                roleRepository.save(new Role(Roles.valueOf(role.name())));
            }
        });
    }
}
