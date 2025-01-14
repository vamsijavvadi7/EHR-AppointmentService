package com.ehr.appointmentservice.service;

import com.ehr.appointmentservice.dao.AppointmentRepository;
import com.ehr.appointmentservice.dto.*;
import com.ehr.appointmentservice.feign.PatientClient;
import com.ehr.appointmentservice.feign.DoctorClient;
import com.ehr.appointmentservice.mapper.AppointmentMapper;
import com.ehr.appointmentservice.model.Appointment;
import com.ehr.appointmentservice.model.VisitInfo;
import com.ehr.appointmentservice.service.kafka.AppointmentProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    @Autowired
    AppointmentProducer appointmentProducer;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private AppointmentMapper appointmentMapper;

    @Autowired
    private PatientClient patientClient;

    @Autowired
    private DoctorClient doctorClient;

    public ResponseEntity<?> createAppointment(AppointmentDto appointmentDto) {


        // Validate patient
        try {
            Optional<PatientDto> patientOptional = Optional.ofNullable(patientClient.getPatientById(appointmentDto.getPatientId()));
            if (!patientOptional.isPresent()) {
                return new ResponseEntity<>("Patient not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Patient service unavailable", HttpStatus.SERVICE_UNAVAILABLE);
        }

        // Validate doctor
        try {
            DoctorDto doctor = doctorClient.getDoctorById(appointmentDto.getDoctorId());

            if (doctor == null) {
                return new ResponseEntity<>("Doctor not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Doctor service unavailable"+e, HttpStatus.SERVICE_UNAVAILABLE);
        }

        // Create appointment
        Appointment appointment = appointmentMapper.toEntity(appointmentDto);
        appointment.getVisitInfo().setAppointment(appointment);
        AppointmentDto savedAppointmentDto = appointmentMapper.toDto(appointmentRepository.save(appointment));

        savedAppointmentDto.getVisitInfo().setAppointmentId(savedAppointmentDto.getId());

        AppointmentDto2 appointmentDto2= appointmentMapper.toAppointmentDto2(savedAppointmentDto);

        appointmentDto2.setPatient(patientClient.getPatientById(appointmentDto2.getPatientId()));

        appointmentProducer.notifyDoctor(appointmentDto2);
        return new ResponseEntity<>(savedAppointmentDto, HttpStatus.CREATED);
    }

    public ResponseEntity<AppointmentDto> getAppointment(Long id) {
        Optional<Appointment> appointment = appointmentRepository.findById(id);
        return appointment.map(value -> ResponseEntity.ok(appointmentMapper.toDto(value)))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<List<AppointmentDto>> getAllAppointments() {
        List<AppointmentDto> appointments = appointmentRepository.findAll().stream()
                .map(appointmentMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(appointments);
    }

    public ResponseEntity<AppointmentDto> updateAppointmentStatus(Long id, String status) {
        Optional<Appointment> appointmentOptional = appointmentRepository.findById(id);

        if (appointmentOptional.isPresent()) {
            Appointment appointment = appointmentOptional.get();
            appointment.setStatus(status);
            Appointment updatedAppointment = appointmentRepository.save(appointment);
            return new ResponseEntity<>(appointmentMapper.toDto(updatedAppointment), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<VisitInfoDto> addVisitInfo(VisitInfoDto visitInfoDto) {
        Long appointmentId = visitInfoDto.getAppointmentId();
        Optional<Appointment> appointmentOptional = appointmentRepository.findById(appointmentId);

        if (appointmentOptional.isPresent()) {
            Appointment appointment = appointmentOptional.get();
            appointment.setStatus("Completed");
            VisitInfo visitInfo = appointment.getVisitInfo(); // Retrieve existing VisitInfo
            if (visitInfo == null) {
                // If no VisitInfo exists, create a new one
                visitInfo = appointmentMapper.toVisitInfoEntity(visitInfoDto);
                visitInfo.setAppointment(appointment);
            } else {
                // Update existing VisitInfo with data from DTO
                visitInfo.setCheckInTime(visitInfoDto.getCheckInTime());
                visitInfo.setCheckOutTime(visitInfoDto.getCheckOutTime());
                visitInfo.setNotes(visitInfoDto.getNotes());
            }

            appointment.setVisitInfo(visitInfo); // Link updated or new VisitInfo to Appointment
            appointmentRepository.save(appointment); // Save changes to the database

            return new ResponseEntity<>(appointmentMapper.toVisitInfoDto(visitInfo), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<List<AppointmentDto>> getPreviousVisitInfo(Long patientid) {
            // Retrieve appointments for the given patient ID
            List<Appointment> appointments = appointmentRepository.findByPatientId(patientid);

            if (!appointments.isEmpty()) {
                // Map the retrieved appointments to AppointmentDto
                List<AppointmentDto> appointmentDtos = appointments.stream()
                        .map(appointment -> {
                            AppointmentDto dto = appointmentMapper.toDto(appointment);
                            dto.getVisitInfo().setAppointmentId(dto.getId());
                            return dto;
                        })
                        .collect(Collectors.toList());

                // Return the appointments
                return new ResponseEntity<>(appointmentDtos, HttpStatus.OK);
            } else {
                // Return NOT_FOUND if no appointments exist for the patient
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
    }
}
