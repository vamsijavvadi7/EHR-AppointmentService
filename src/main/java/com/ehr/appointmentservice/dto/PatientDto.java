package com.ehr.appointmentservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PatientDto {
    private Long id;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Email
    private String email;

    @NotBlank
    private String phone;

    private AddressDto address;


    @Data
    @NoArgsConstructor
    public class AddressDto {
        private String street;
        private String city;
        private String state;
        private String postalCode;
    }

}

