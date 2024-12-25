package com.ehr.appointmentservice.feign;


import com.ehr.appointmentservice.dto.DoctorDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "doctorservice")
public interface DoctorClient {

    @GetMapping("/doctor/{id}")
    DoctorDto getDoctorById(@PathVariable("id") Long id);

}