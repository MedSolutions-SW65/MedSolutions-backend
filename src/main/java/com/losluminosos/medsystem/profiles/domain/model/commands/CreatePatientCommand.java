package com.losluminosos.medsystem.profiles.domain.model.commands;

public record CreatePatientCommand(String uid,String firstName, String lastName, String email, String phone, String street, String number, String city, String postalCode, String country) {}
