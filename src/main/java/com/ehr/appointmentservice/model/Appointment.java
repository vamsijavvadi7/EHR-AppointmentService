package com.ehr.appointmentservice.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "appointment")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "patient_id")
    private Long patientId; // References PatientService
    @Column(name = "doctor_id")
    private Long doctorId; // References DoctorService
    @Column(name = "appointment_time")
    private LocalDateTime appointmentTime;
    @Column(name = "status")
    private String status; // e.g., "Scheduled", "Completed", "Cancelled"
    @OneToOne(mappedBy = "appointment", cascade = CascadeType.ALL)
    private VisitInfo visitInfo;
}

