package com.ehr.appointmentservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@Entity
public class VisitInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;
    private LocalTime checkInTime;
    private LocalTime checkOutTime;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String notes; // Diagnosis, treatment, and any other notes from the visit
}
