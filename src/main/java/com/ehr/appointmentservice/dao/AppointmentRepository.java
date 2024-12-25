package com.ehr.appointmentservice.dao;
import com.ehr.appointmentservice.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("SELECT a FROM Appointment as a WHERE a.doctorId = :doctorId " +
            "AND a.appointmentTime BETWEEN :startOfDay AND :endOfDay " +
            "AND Date(a.appointmentTime) = :date")
    List<Appointment> findAppointmentsByDoctorIdAndDate(
            @Param("doctorId") Long doctorId,
            @Param("date") LocalDate date,
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay
    );

    List<Appointment> findByPatientId(Long patientid);
}
