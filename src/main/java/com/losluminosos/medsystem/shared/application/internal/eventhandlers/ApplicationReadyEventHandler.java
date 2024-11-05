package com.losluminosos.medsystem.shared.application.internal.eventhandlers;

import com.losluminosos.medsystem.auth.domain.model.commands.SeedRolesCommand;
import com.losluminosos.medsystem.auth.domain.services.RoleCommandService;
import com.losluminosos.medsystem.shared.domain.model.commands.SeedSpecialtiesCommand;
import com.losluminosos.medsystem.shared.domain.services.SpecialtyCommandService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class ApplicationReadyEventHandler {
    private final SpecialtyCommandService specialtyCommandService;
    private final RoleCommandService roleCommandService;

    public ApplicationReadyEventHandler(SpecialtyCommandService specialtyCommandService, RoleCommandService roleCommandService) {
        this.specialtyCommandService = specialtyCommandService;
        this.roleCommandService = roleCommandService;
    }

    @EventListener
    public void on(ApplicationReadyEvent event){
        var seedSpecialtiesCommand = new SeedSpecialtiesCommand();
        specialtyCommandService.handle(seedSpecialtiesCommand);
        var seedRolesCommand = new SeedRolesCommand();
        roleCommandService.handle(seedRolesCommand);
    }
}
