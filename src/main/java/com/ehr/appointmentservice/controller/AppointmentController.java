package com.ehr.appointmentservice.controller;


import com.ehr.appointmentservice.dto.AppointmentDto;
import com.ehr.appointmentservice.dto.PatientDto;
import com.ehr.appointmentservice.dto.VisitInfoDto;
import com.ehr.appointmentservice.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/create")
    public ResponseEntity<?> scheduleAppointment(@RequestBody AppointmentDto appointmentDto) {
        return appointmentService.createAppointment(appointmentDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDto> getAppointment(@PathVariable Long id) {
        return appointmentService.getAppointment(id);
    }

    @GetMapping
    public ResponseEntity<List<AppointmentDto>> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentDto> updateAppointmentStatus(@PathVariable Long id, @RequestBody String status) {
        return appointmentService.updateAppointmentStatus(id, status);
    }

    @PostMapping("/visit-info")
    public ResponseEntity<VisitInfoDto> addVisitInfo(@RequestBody VisitInfoDto visitInfoDto) {
        return appointmentService.addVisitInfo(visitInfoDto);
    }

    @GetMapping("/previousvisitinfo/{patientid}")
    public ResponseEntity<List<AppointmentDto>> getPreviousVisitInfo(@PathVariable Long patientid) {
        return appointmentService.getPreviousVisitInfo(patientid);
    }

}

