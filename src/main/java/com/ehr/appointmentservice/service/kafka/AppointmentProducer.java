package com.ehr.appointmentservice.service.kafka;
import com.ehr.appointmentservice.dto.AppointmentDto;
import com.ehr.appointmentservice.dto.AppointmentDto2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class AppointmentProducer {

    private final KafkaTemplate<String, AppointmentDto2> kafkaTemplate;

    private static final String TOPIC = "appointments";
    @Autowired
    public AppointmentProducer(KafkaTemplate<String, AppointmentDto2> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    public void notifyDoctor(AppointmentDto2 message) {
        kafkaTemplate.send(TOPIC, message);
    }
}
