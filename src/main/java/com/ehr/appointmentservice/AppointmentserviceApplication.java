package com.ehr.appointmentservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AppointmentserviceApplication {
	public static void main(String[] args) {
		SpringApplication.run(AppointmentserviceApplication.class, args);
	}
}
