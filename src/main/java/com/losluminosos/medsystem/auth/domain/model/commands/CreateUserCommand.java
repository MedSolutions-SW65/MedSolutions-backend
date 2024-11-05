package com.losluminosos.medsystem.auth.domain.model.commands;

public record CreateUserCommand(String username, String password, String role) {
}
