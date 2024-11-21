package com.losluminosos.medsystem.emailservice.application.internal.commands.commanServices;

import com.losluminosos.medsystem.emailservice.domain.model.Aggregates.Email;
import com.losluminosos.medsystem.emailservice.domain.model.Commands.SendEmailCommand;
import com.losluminosos.medsystem.emailservice.infrastructure.EmailSender;
import jakarta.mail.MessagingException;

import org.springframework.stereotype.Service;

@Service
public class EmailCommandServiceImpl {
    private final EmailSender emailSender;


    public EmailCommandServiceImpl(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    // cuando el usaurio se registra se envia un mensaje de bienvenida a su correo (estatico)
    public void handle(SendEmailCommand command) throws MessagingException {
        Email email = new Email(command);
        emailSender.send(email);
    }

    // cuando el usuario reserva una cita se envia a su correo el recordatorio con el detalle de la cita (dinamico)


    // Cuando el usuario cancela su cita, se envia un correo de cancelacion (estatico)
}
