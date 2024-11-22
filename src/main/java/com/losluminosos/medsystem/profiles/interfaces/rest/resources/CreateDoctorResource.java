package com.losluminosos.medsystem.profiles.interfaces.rest.resources;

public record CreateDoctorResource(
        String uid, // UID enviado desde el frontend
        String firstName,
        String lastName,
        String email,
        String phone,
        String licenceNumber,
        String specialty
) {}
