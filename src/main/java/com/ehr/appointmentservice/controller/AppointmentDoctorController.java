package com.ehr.appointmentservice.controller;


import com.ehr.appointmentservice.dto.AppointmentDto;
import com.ehr.appointmentservice.service.DoctorAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/doctorappointment")
public class AppointmentDoctorController {

    @Autowired
    DoctorAppointmentService doctorAppointmentService;

    @GetMapping("/appointments")
    public ResponseEntity<Object> getAppointmentsOfDoctor(
            @RequestParam Long doctorId,
            @RequestParam LocalDate date,
            @RequestParam LocalTime startTime,
            @RequestParam LocalTime endTime) {

        return doctorAppointmentService.getAllAppointmentsOfDoctor(doctorId, date, startTime, endTime);
    }


}
