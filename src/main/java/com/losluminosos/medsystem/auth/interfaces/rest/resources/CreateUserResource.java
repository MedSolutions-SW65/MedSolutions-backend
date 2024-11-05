package com.losluminosos.medsystem.auth.interfaces.rest.resources;

public record CreateUserResource(String username, String password, String role) {
}
