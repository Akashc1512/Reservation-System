package com.tcs.customers.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcs.customers.dto.Notification;
import com.tcs.customers.dto.ReservationRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaService {

    @Autowired
    private NewTopic topic;

    @Autowired
    private NewTopic topicBooking;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendNotification(Notification notification) throws Exception{

        ObjectMapper mapper = new ObjectMapper();
        String messagewrite = mapper.writeValueAsString(notification);
        Message<String> message = MessageBuilder
                .withPayload(messagewrite)
                .setHeader(KafkaHeaders.TOPIC, topic.name())
                .build();
        kafkaTemplate.send(message);
        log.debug("notification message send");
    }

    public void sendBookingNotification(ReservationRequest reservation) throws Exception{

        ObjectMapper mapper = new ObjectMapper();
        String messagewrite = mapper.writeValueAsString(reservation);
        Message<String> message = MessageBuilder
                .withPayload(messagewrite)
                .setHeader(KafkaHeaders.TOPIC, topicBooking.name())
                .build();
        kafkaTemplate.send(message);
        log.debug("booking message send");
    }
}
