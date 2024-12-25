package com.ehr.appointmentservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
public class VisitInfoDto {
    private Long id;
    private Long appointmentId;
    private LocalTime checkInTime;
    private LocalTime checkOutTime;
    private String notes;
}
