package com.losluminosos.medsystem.emailservice.interfaces.rest;

import com.losluminosos.medsystem.emailservice.application.internal.commands.commanServices.EmailCommandServiceImpl;
import com.losluminosos.medsystem.emailservice.domain.model.Commands.SendEmailCommand;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email")
public class EmailController {
    private final EmailCommandServiceImpl emailService;

    public EmailController(EmailCommandServiceImpl emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send-email")
    public ResponseEntity<String> sendEmail(@RequestBody SendEmailCommand command){
        try {
            emailService.handle(command);
            return ResponseEntity.ok("Email sent");
        } catch (MessagingException e) {
            return ResponseEntity.status(500).body("Failed to send email" + e.getMessage());
        }

    }
}
