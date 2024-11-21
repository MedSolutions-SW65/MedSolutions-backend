package com.losluminosos.medsystem.emailservice.domain.model.Commands;

public record SendEmailCommand(String to, String subject, String body) {
}
