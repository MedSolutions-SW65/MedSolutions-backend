package com.losluminosos.medsystem.profiles.interfaces.rest;

import com.losluminosos.medsystem.emailservice.application.internal.commands.commanServices.EmailCommandServiceImpl;
import com.losluminosos.medsystem.emailservice.domain.model.Commands.SendEmailCommand;
import com.losluminosos.medsystem.profiles.domain.model.queries.GetAllDoctorsQuery;
import com.losluminosos.medsystem.profiles.domain.model.queries.GetDoctorByIdQuery;
import com.losluminosos.medsystem.profiles.domain.model.queries.GetDoctorByUidQuery;
import com.losluminosos.medsystem.profiles.domain.services.DoctorCommandService;
import com.losluminosos.medsystem.profiles.domain.services.DoctorQueryService;
import com.losluminosos.medsystem.profiles.interfaces.rest.resources.CreateDoctorResource;
import com.losluminosos.medsystem.profiles.interfaces.rest.resources.DoctorResource;
import com.losluminosos.medsystem.profiles.interfaces.rest.transform.CreateDoctorCommandFromResourceAssembler;
import com.losluminosos.medsystem.profiles.interfaces.rest.transform.DoctorResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/doctors", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Doctors", description = "Doctor Management Endpoints")
public class DoctorsController {
    private final DoctorQueryService doctorQueryService;
    private final DoctorCommandService doctorCommandService;
    private final EmailCommandServiceImpl emailService;

    public DoctorsController(DoctorQueryService doctorQueryService, DoctorCommandService doctorCommandService, EmailCommandServiceImpl emailService) {
        this.doctorQueryService = doctorQueryService;
        this.doctorCommandService = doctorCommandService;
        this.emailService = emailService;
    }

    @PostMapping
    public ResponseEntity<DoctorResource> createDoctor(@RequestBody CreateDoctorResource resource) {
        var createDoctorCommand = CreateDoctorCommandFromResourceAssembler.toCommandFromResource(resource);
        var doctor = doctorCommandService.handle(createDoctorCommand);
        if (doctor.isEmpty()) return ResponseEntity.badRequest().build();

        // Enviar correo al doctor recién registrado
        try {
            var sendEmailCommand = new SendEmailCommand(
                    resource.email(),    // Destinatario (correo del doctor)
                    "Bienvenido a MedSystem",  // Asunto
                    "Hola " + resource.firstName() + " "+ resource.lastName() + " te damos la bienvenida a Medsystem. Se ha registrado como doctor" // Contenido
            );
            emailService.handle(sendEmailCommand);
        } catch (MessagingException e) {
            return ResponseEntity.status(500).body(null); // Manejo de error en caso de fallo al enviar el correo
        }

        var doctorResource = DoctorResourceFromEntityAssembler.toResourceFromEntity(doctor.get());
        return new ResponseEntity<>(doctorResource, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<DoctorResource>> getAllDoctors(){
        var getAllDoctorsQuery = new GetAllDoctorsQuery();
        var doctors = doctorQueryService.handle(getAllDoctorsQuery);
        var doctorResources = doctors.stream().map(DoctorResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(doctorResources);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorResource> getDoctorById(@PathVariable Long id) {
        var getDoctorByIdQuery = new GetDoctorByIdQuery(id);
        var doctor = doctorQueryService.handle(getDoctorByIdQuery);
        if (doctor.isEmpty())
            return ResponseEntity.notFound().build();
        var doctorResource = DoctorResourceFromEntityAssembler.toResourceFromEntity(doctor.get());
        return ResponseEntity.ok(doctorResource);
    }

    @GetMapping("/uid/{uid}")
    public ResponseEntity<DoctorResource> getDoctorByUid(@PathVariable String uid) {
        var getDoctorByUidQuery = new GetDoctorByUidQuery(uid);
        var doctor = doctorQueryService.handle(getDoctorByUidQuery);
        if (doctor.isEmpty()) return ResponseEntity.notFound().build();
        var doctorResource = DoctorResourceFromEntityAssembler.toResourceFromEntity(doctor.get());
        return ResponseEntity.ok(doctorResource);
    }

}
