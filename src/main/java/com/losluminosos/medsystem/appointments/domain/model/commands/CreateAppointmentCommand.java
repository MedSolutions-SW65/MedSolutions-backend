package com.losluminosos.medsystem.appointments.domain.model.commands;

import com.losluminosos.medsystem.shared.domain.model.entities.Specialty;

import java.util.Date;

public record CreateAppointmentCommand(Long doctorId, Long patientId, String date, String reason, String specialty) {
}
