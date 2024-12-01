package com.tcs.reservations.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tcs.reservations.dto.Hotels;
import com.tcs.reservations.dto.Notification;
import com.tcs.reservations.dto.Payment;
import com.tcs.reservations.dto.ReservationRequest;
import com.tcs.reservations.entities.Reservation;
import com.tcs.reservations.repository.ReservationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ReservationService {

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    ApiGatewayClient apiGatewayClient;

    @Autowired
    private KafkaService kafkaService;

    // Receiving Message from Customer Service Where Make Reservation Initiated
    @KafkaListener(topics = "${spring.kafka.topic.booking}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void reservationProcess(String reservationPayload) throws Exception {
        log.info("message {}", reservationPayload);

        ObjectMapper mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        ReservationRequest reservationRequest = null;
        try {
            reservationRequest = mapper.readValue(reservationPayload, ReservationRequest.class);
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (reservationRequest != null) {
            Hotels hotel = apiGatewayClient.getHotel(reservationRequest.getHotelId());
            if (hotel != null && "AVAILABLE".equalsIgnoreCase(hotel.getStatus())) {
                Reservation reservation = reservationRepository.save(Reservation.builder()
                                .HotelId(reservationRequest.getHotelId())
                                .CustomerId(reservationRequest.getCustomerId())
                                .BookingAmount(reservationRequest.getBookingAmount())
                                .StartDate(reservationRequest.getStartDate())
                                .EndDate(reservationRequest.getEndDate())
                                .Status("IN-PROGRESS")
                        .build());


                try {
                    Payment payment = new Payment();
                    payment.setCustomerId(reservationRequest.getCustomerId());
                    payment.setAmount(reservationRequest.getBookingAmount());
                    payment.setStatus("PAID");
                    Payment paid = apiGatewayClient.makePayment(payment);
                    reservation.setPaymentId(paid.getId());
                } catch (Exception ex){
                    ex.printStackTrace();

                    reservation.setStatus("FAILED");
                    reservationRepository.save(reservation);
                    kafkaService.sendNotification(Notification.builder()
                            .CustomerId(reservationRequest.getCustomerId())
                            .eventType("Payment Failed")
                            .message("Your Room is not booked for Hotel " + hotel.getName()
                                    + " For Date : " + reservationRequest.getStartDate().toString()
                                    + " To Date : " + reservationRequest.getEndDate().toString()
                                    + " \n Reason : Payment Failed")
                            .build());
                    throw new Exception("Payment Failed :: " + ex.getMessage());
                }
                apiGatewayClient.updateHotelBookingStatus(hotel.getId(), "BOOKED");

                kafkaService.sendNotification(Notification.builder()
                        .CustomerId(reservationRequest.getCustomerId())
                        .eventType("Booking confirmed")
                        .message("Your Room is Booked in Hotel " + hotel.getName()
                                + " For Date : " + reservationRequest.getStartDate().toString()
                                + " To Date : " + reservationRequest.getEndDate().toString())
                        .build());
                reservation.setStatus("COMPLETED");
                reservationRepository.save(reservation);
            } else {
                kafkaService.sendNotification(Notification.builder()
                        .CustomerId(reservationRequest.getCustomerId())
                        .eventType("Booking not confirmed")
                        .message("Your Room is not available for Hotel " + hotel.getName()
                                + " For Date : " + reservationRequest.getStartDate().toString()
                                + " To Date : " + reservationRequest.getEndDate().toString())
                        .build());
            }
        } else {
            log.info("no message present");
        }
    }

}
