package com.losluminosos.medsystem.auth.domain.services;

import com.losluminosos.medsystem.auth.domain.model.commands.SeedRolesCommand;

public interface RoleCommandService {
    void handle(SeedRolesCommand command);
}