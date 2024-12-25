package com.ehr.appointmentservice.service.kafka;
import com.ehr.appointmentservice.dto.AppointmentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class AppointmentProducer {

    private final KafkaTemplate<String, AppointmentDto> kafkaTemplate;

    private static final String TOPIC = "appointments";
    @Autowired
    public AppointmentProducer(KafkaTemplate<String, AppointmentDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    public void notifyDoctor(AppointmentDto message) {
        kafkaTemplate.send(TOPIC, message);
        System.out.println("Appointment notification sent to Kafka: " + message);
    }
}
