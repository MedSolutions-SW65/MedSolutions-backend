package com.losluminosos.medsystem.appointments.interfaces.rest.resources;

public record AppointmentResource(
        Long id,
        Long doctorId,
        Long patientId,
        String date,
        String reason,
        String specialty
) { }
