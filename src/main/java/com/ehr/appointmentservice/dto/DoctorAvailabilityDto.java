package com.ehr.appointmentservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class DoctorAvailabilityDto {
    @NotBlank
    private String availableDays; // e.g., "Mon-Fri"

    @NotBlank
    private String availableFrom; // e.g., "09:00"

    @NotBlank
    private String availableUntil; // e.g., "17:00"
}
