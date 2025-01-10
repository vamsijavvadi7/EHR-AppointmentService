package com.ehr.appointmentservice.mapper;

import com.ehr.appointmentservice.dto.AppointmentDto;
import com.ehr.appointmentservice.dto.AppointmentDto2;
import com.ehr.appointmentservice.dto.VisitInfoDto;
import com.ehr.appointmentservice.model.Appointment;
import com.ehr.appointmentservice.model.VisitInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {


    // Mapping Appointment entity to AppointmentDto
    AppointmentDto toDto(Appointment appointment);

    // Mapping AppointmentDto to Appointment entity
    Appointment toEntity(AppointmentDto appointmentDto);

    // Mapping VisitInfo entity to VisitInfoDto
    VisitInfoDto toVisitInfoDto(VisitInfo visitInfo);

    // Mapping VisitInfoDto to VisitInfo entity
    @Mapping(target = "appointment", ignore = true) // Prevent circular reference
    VisitInfo toVisitInfoEntity(VisitInfoDto visitInfoDto);

    AppointmentDto2 toAppointmentDto2(AppointmentDto appointmentDto);
}

