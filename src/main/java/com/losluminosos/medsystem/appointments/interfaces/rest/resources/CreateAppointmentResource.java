package com.losluminosos.medsystem.appointments.interfaces.rest.resources;

public record CreateAppointmentResource(
        Long doctorId,
        Long patientId,
        String date,
        String reason,
        String specialty
){ }
