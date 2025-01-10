package com.ehr.appointmentservice.service;

import com.ehr.appointmentservice.dao.AppointmentRepository;
import com.ehr.appointmentservice.dto.AppointmentDto;
import com.ehr.appointmentservice.dto.DoctorDto;
import com.ehr.appointmentservice.feign.DoctorClient;
import com.ehr.appointmentservice.mapper.AppointmentMapper;
import com.ehr.appointmentservice.model.Appointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorAppointmentService {

    @Autowired
    AppointmentRepository appointmentRepository;
    @Autowired
    AppointmentMapper appointmentMapper;

    @Autowired
    private DoctorClient doctorClient;


    public ResponseEntity<Object> getAllAppointmentsOfDoctor(Long doctorId,
                                                             LocalDate date,
                                                             LocalTime startTime,
                                                             LocalTime endTime) {

        // Validate doctor
        try {
            DoctorDto doctor = doctorClient.getDoctorById(doctorId);
            if (doctor == null) {
                return new ResponseEntity<>("Doctor not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Doctor service unavailable due to a exception", HttpStatus.SERVICE_UNAVAILABLE);
        }

        // Default to today's start and end of day if no time range is provided
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);

        // If startTime and endTime are provided, use them; otherwise, use startOfDay and endOfDay
        LocalDateTime startDateTime = (startTime != null) ? date.atTime(startTime) : startOfDay;
        LocalDateTime endDateTime = (endTime != null) ? date.atTime(endTime) : endOfDay;


        // Fetch appointments based on the given doctorId and date range
        List<Appointment> appointments = appointmentRepository.findAppointmentsByDoctorIdAndDate(
                doctorId, date, startDateTime,endDateTime
        );


        List<AppointmentDto> appointmentDtos = appointments.stream()
                .map((appointment) -> {
                    AppointmentDto appointmentDto= appointmentMapper.toDto(appointment);
                appointmentDto.getVisitInfo().setAppointmentId(appointmentDto.getId());
                return appointmentDto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(appointmentDtos);
    }

}
