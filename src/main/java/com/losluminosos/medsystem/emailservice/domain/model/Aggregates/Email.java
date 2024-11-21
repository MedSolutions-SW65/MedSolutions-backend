package com.losluminosos.medsystem.emailservice.domain.model.Aggregates;

import com.losluminosos.medsystem.emailservice.domain.model.Commands.SendEmailCommand;
import com.losluminosos.medsystem.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;

@Getter
@Entity
public class Email extends AuditableAbstractAggregateRoot<Email> {

    @Column(name = "to_email")
    private String to;

    @Column(name = "subject")
    private String subject;

    @Column(name = "body")
    private String body;

    public Email(){
        this.to = "";
        this.subject = "";
        this.body = "";
    }

    public Email(SendEmailCommand command) {
        this();
        this.to = command.to();
        this.subject = command.subject();
        this.body = command.body();
    }
}
