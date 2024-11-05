package com.losluminosos.medsystem.auth.domain.services;

import com.losluminosos.medsystem.auth.domain.model.aggregates.User;
import com.losluminosos.medsystem.auth.domain.model.commands.CreateUserCommand;

import java.util.Optional;

public interface UserCommandService {
    Optional<User> handle(CreateUserCommand command);
}
