package com.losluminosos.medsystem.auth.interfaces.rest;

import com.losluminosos.medsystem.auth.domain.model.entities.Role;
import com.losluminosos.medsystem.auth.domain.model.queries.*;
import com.losluminosos.medsystem.auth.domain.services.UserCommandService;
import com.losluminosos.medsystem.auth.domain.services.UserQueryService;
import com.losluminosos.medsystem.auth.interfaces.rest.resources.CreateUserResource;
import com.losluminosos.medsystem.auth.interfaces.rest.resources.UserResource;
import com.losluminosos.medsystem.auth.interfaces.rest.transform.CreateUserCommandFromResourceAssembler;
import com.losluminosos.medsystem.auth.interfaces.rest.transform.UserResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Users", description = "User Management Endpoints")
public class UserController {
    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;

    public UserController(UserCommandService userCommandService, UserQueryService userQueryService) {
        this.userCommandService = userCommandService;
        this.userQueryService = userQueryService;
    }

    @PostMapping
    public ResponseEntity<UserResource> createUser(@RequestBody CreateUserResource resource) {
        var createUserCommand = CreateUserCommandFromResourceAssembler.toCommandFromResource(resource);
        var user = userCommandService.handle(createUserCommand);
        if (user.isEmpty()) return ResponseEntity.badRequest().build();
        var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(user.get());
        return new ResponseEntity<>(userResource, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<UserResource>> getAllUsers(){
        var getAllUsersQuery = new GetAllUsersQuery();
        var users = userQueryService.handle(getAllUsersQuery);
        var userResources = users.stream()
                .map(UserResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(userResources);
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserResource> getUserByUsername(@PathVariable Long id) {
        var getUserById = new GetUserByIdQuery(id);
        var user = userQueryService.handle(getUserById);
        if (user.isEmpty())
            return ResponseEntity.notFound().build();
        var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(user.get());
        return ResponseEntity.ok(userResource);
    }
    @GetMapping("/username/{username}")
    public ResponseEntity<UserResource> getUserByUsername(@PathVariable String username) {
        var getUserByUsername = new GetUserByUsernameQuery(username);
        var user = userQueryService.handle(getUserByUsername);
        if (user.isEmpty())
            return ResponseEntity.notFound().build();
        var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(user.get());
        return ResponseEntity.ok(userResource);
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<List<UserResource>> getUsersByRole(@PathVariable String role) {
        var getUsersByRole = new GetUsersByRole(role);
        var users = userQueryService.handle(getUsersByRole);
        var userResources = users.stream()
                .map(UserResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(userResources);
    }
    @GetMapping("/username/{username}/password/{password}")
    public ResponseEntity<UserResource> getUserByUsernameAndPassword(@PathVariable String username, @PathVariable String password) {
        var getUserByUsernameAndPassword = new GetUserByUsernameAndPassword(username, password);
        var user = userQueryService.handle(getUserByUsernameAndPassword);
        if (user.isEmpty())
            return ResponseEntity.notFound().build();
        var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(user.get());
        return ResponseEntity.ok(userResource);
    }
}
