package com.losluminosos.medsystem.appointments.interfaces.rest;

import com.losluminosos.medsystem.appointments.domain.model.commands.DeleteAppointmentCommand;
import com.losluminosos.medsystem.appointments.domain.model.queries.GetAllAppointmentsByDoctorIdQuery;
import com.losluminosos.medsystem.appointments.domain.model.queries.GetAllAppointmentsByPatientIdQuery;
import com.losluminosos.medsystem.appointments.domain.model.queries.GetAllAppointmentsQuery;
import com.losluminosos.medsystem.appointments.domain.model.queries.GetAppointmentByIdQuery;
import com.losluminosos.medsystem.appointments.domain.services.AppointmentCommandService;
import com.losluminosos.medsystem.appointments.domain.services.AppointmentQueryService;
import com.losluminosos.medsystem.appointments.interfaces.rest.resources.AppointmentResource;
import com.losluminosos.medsystem.appointments.interfaces.rest.resources.CreateAppointmentResource;
import com.losluminosos.medsystem.appointments.interfaces.rest.resources.UpdateAppointmentReasonResource;
import com.losluminosos.medsystem.appointments.interfaces.rest.transform.AppointmentResourceFromEntityAssembler;
import com.losluminosos.medsystem.appointments.interfaces.rest.transform.CreateAppointmentCommandFromResourceAssembler;
import com.losluminosos.medsystem.appointments.interfaces.rest.transform.UpdateAppointmentReasonCommandFromResourceAssembler;
import com.losluminosos.medsystem.emailservice.application.internal.commands.commanServices.EmailCommandServiceImpl;
import com.losluminosos.medsystem.emailservice.domain.model.Commands.SendEmailCommand;
import com.losluminosos.medsystem.profiles.infrastructure.persistence.jpa.repositories.DoctorRepository;
import com.losluminosos.medsystem.profiles.infrastructure.persistence.jpa.repositories.PatientRepository;
import com.losluminosos.medsystem.profiles.interfaces.rest.transform.DoctorResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/appointments", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Appointments", description = "Appointment Management Endpoints")
public class AppointmentsController {
    private final AppointmentCommandService appointmentCommandService;
    private final AppointmentQueryService appointmentQueryService;
    private final EmailCommandServiceImpl emailService;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    public AppointmentsController(AppointmentCommandService appointmentCommandService, AppointmentQueryService appointmentQueryService, EmailCommandServiceImpl emailService, PatientRepository patientRepository, DoctorRepository doctorRepository){
        this.appointmentCommandService = appointmentCommandService;
        this.appointmentQueryService = appointmentQueryService;
        this.emailService = emailService;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }

    @PostMapping
    public ResponseEntity<AppointmentResource> createAppointment(@RequestBody CreateAppointmentResource resource) {
        var createAppointmentCommand = CreateAppointmentCommandFromResourceAssembler.toCommandFromResource(resource);
        var appointment = appointmentCommandService.handle(createAppointmentCommand);
        if (appointment.isEmpty()) return ResponseEntity.badRequest().build();


        var doctor = doctorRepository.findById(resource.doctorId());
        var patient = patientRepository.findById(resource.patientId());

        if (doctor.isEmpty() || patient.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }

        String doctorName = doctor.get().getFullName();
        String patientEmail = patient.get().getEmailAddress();
        String patientName = patient.get().getFullName();


        try {
            var sendEmailCommand = new SendEmailCommand(
                    patientEmail,
                    "Confirmación de cita en MedSystem",
                    "Estimado(a) " + patientName + ",\n\n" +
                            "Su cita con el doctor " + doctorName +
                            " ha sido confirmada para el día " + resource.date() +
                            ".\n\nGracias por usar MedSystem."
            );
            emailService.handle(sendEmailCommand);
        } catch (MessagingException e) {
            return ResponseEntity.status(500).body(null);
        }

        var appointmentResource = AppointmentResourceFromEntityAssembler.toResourceFromEntity(appointment.get());
        return new ResponseEntity<>(appointmentResource, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AppointmentResource>> getAllAppointments(){
        var getAllAppointmentsQuery = new GetAllAppointmentsQuery();
        var appointments = appointmentQueryService.handle(getAllAppointmentsQuery);
        var appointmentResources = appointments.stream()
                .map(AppointmentResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(appointmentResources);
    }
    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResource> getAppointmentById(@PathVariable Long id) {
        var getAppointmentByIdQuery = new GetAppointmentByIdQuery(id);
        var appointment = appointmentQueryService.handle(getAppointmentByIdQuery);
        if (appointment.isEmpty())
            return ResponseEntity.notFound().build();
        var appointmentResource = AppointmentResourceFromEntityAssembler.toResourceFromEntity(appointment.get());
        return ResponseEntity.ok(appointmentResource);
    }
    @GetMapping("/patientId/{patientId}")
    public ResponseEntity<List<AppointmentResource>> getAllAppointmentByPatientId(@PathVariable Long patientId) {
        var getAllAppointmentsByPatientIdQuery = new GetAllAppointmentsByPatientIdQuery(patientId);
        var appointments = appointmentQueryService.handle(getAllAppointmentsByPatientIdQuery);
        var appointmentsResources = appointments.stream()
                .map(AppointmentResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(appointmentsResources);
    }
    @GetMapping("/doctorId/{doctorId}")
    public ResponseEntity<List<AppointmentResource>> getAllAppointmentByDoctorId(@PathVariable Long doctorId) {
        var getAllAppointmentsByDoctorQuery = new GetAllAppointmentsByDoctorIdQuery(doctorId);
        var appointments = appointmentQueryService.handle(getAllAppointmentsByDoctorQuery);
        var appointmentsResources = appointments.stream()
                .map(AppointmentResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(appointmentsResources);
    }

    @PutMapping ("/{appointmentId}")
    public ResponseEntity<AppointmentResource> updateAppointmentReason(@PathVariable Long appointmentId, @RequestBody UpdateAppointmentReasonResource updateAppointmentReasonResource) {
        var updateAppointmentReasonCommand = UpdateAppointmentReasonCommandFromResourceAssembler.toCommandFromResource(appointmentId, updateAppointmentReasonResource);
        var updatedAppointment = appointmentCommandService.handle(updateAppointmentReasonCommand);
        if (updatedAppointment.isEmpty()) return ResponseEntity.badRequest().build();
        var appointmentResource = AppointmentResourceFromEntityAssembler.toResourceFromEntity(updatedAppointment.get());
        return ResponseEntity.ok(appointmentResource);
    }

    @DeleteMapping("/{appointmentId}")
    public ResponseEntity<?> deleteAppointment(@PathVariable Long appointmentId) {
        var deleteAppointmentCommand = new DeleteAppointmentCommand(appointmentId);
        appointmentCommandService.handle(deleteAppointmentCommand);
        return ResponseEntity.ok("Course with given id successfully deleted");
    }
}
