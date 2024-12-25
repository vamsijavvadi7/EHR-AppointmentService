package com.ehr.appointmentservice.feign;

import com.ehr.appointmentservice.dto.PatientDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "patient")
public interface PatientClient {

    @GetMapping("/patients/{id}")
    PatientDto getPatientById(@PathVariable("id") Long id);

}