package com.losluminosos.medsystem.profiles.domain.model.commands;

public record CreateDoctorCommand(String uid, String firstName, String lastName, String email, String phone, String licenceNumber, String specialty) {}

