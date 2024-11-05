package com.losluminosos.medsystem.auth.domain.services;

import com.losluminosos.medsystem.auth.domain.model.aggregates.User;
import com.losluminosos.medsystem.auth.domain.model.queries.*;

import java.util.List;
import java.util.Optional;

public interface UserQueryService {
    List<User> handle(GetAllUsersQuery query);
    Optional<User> handle(GetUserByUsernameQuery query);
    Optional<User> handle(GetUserByIdQuery query);
    Optional<User> handle(GetUserByUsernameAndPassword query);
    List<User> handle(GetUsersByRole query);
}
