package com.losluminosos.medsystem.profiles.interfaces.rest.resources;

public record CreatePatientResource(
        String uid,
        String firstName,
        String lastName,
        String email,
        String phone,
        String street,
        String number,
        String city,
        String postalCode,
        String country
) {
}
