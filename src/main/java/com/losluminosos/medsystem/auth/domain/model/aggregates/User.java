package com.losluminosos.medsystem.auth.domain.model.aggregates;

import com.losluminosos.medsystem.auth.domain.model.entities.Role;
import com.losluminosos.medsystem.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class User extends AuditableAbstractAggregateRoot<User> {
    private String username;

    private String password;

    @Getter
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id")
    private Role role;

    public User(){}

    public User(String username, String password, Role role) {
        this();
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
