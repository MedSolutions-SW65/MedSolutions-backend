package com.losluminosos.medsystem.shared.application.internal.eventhandlers;

import com.losluminosos.medsystem.shared.domain.model.commands.SeedSpecialtiesCommand;
import com.losluminosos.medsystem.shared.domain.services.SpecialtyCommandService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class ApplicationReadyEventHandler {
    private final SpecialtyCommandService specialtyCommandService;

    public ApplicationReadyEventHandler(SpecialtyCommandService specialtyCommandService) {
        this.specialtyCommandService = specialtyCommandService;
    }

    @EventListener
    public void on(ApplicationReadyEvent event){
        var seedSpecialtiesCommand = new SeedSpecialtiesCommand();
        specialtyCommandService.handle(seedSpecialtiesCommand);
    }
}
