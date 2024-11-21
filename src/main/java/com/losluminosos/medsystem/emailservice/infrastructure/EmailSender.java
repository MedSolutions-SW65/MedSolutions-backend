package com.losluminosos.medsystem.emailservice.infrastructure;

import com.losluminosos.medsystem.emailservice.domain.model.Aggregates.Email;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
public class EmailSender {
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    public EmailSender(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    public void send(Email email) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            helper.setTo(email.getTo());
            helper.setSubject(email.getSubject());
            //helper.setFrom("galonso097@gmail.com");

            String htmlContent = generateEmailContent(email);
            helper.setText(htmlContent, true);

            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }

    private String generateEmailContent(Email email) {
        Context context = new Context();
        context.setVariable("subject", email.getSubject());
        context.setVariable("body", email.getBody());

        return templateEngine.process("email-welcome-template", context);
    }
}