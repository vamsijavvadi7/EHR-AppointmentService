package com.ehr.appointmentservice.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class DoctorDto {
    private Long id;
    @NotBlank
    private String name;


    @NotBlank
    private String specialization;

    @Email
    private String email;

    @NotBlank
    private String phone;

    private DoctorAvailabilityDto availability;
}
