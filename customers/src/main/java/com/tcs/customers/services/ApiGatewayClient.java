package com.tcs.customers.services;

import com.tcs.customers.dto.Hotels;
import com.tcs.customers.entities.Payment;
import com.tcs.customers.entities.Reservation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "apigateway-service", url = "http://localhost:8888", path = "/")
public interface ApiGatewayClient {

    @GetMapping("/hotels/{HotelId}")
    public Hotels getHotel(@PathVariable Long HotelId);

    @GetMapping("/hotels/{HotelId}/bookingStatus/{Status}")
    public Hotels updateHotelBookingStatus(@PathVariable Long HotelId, @PathVariable String Status);

    @GetMapping("/reservations/{id}")
    public Reservation getReservationById(@PathVariable Long id);

    @GetMapping("/payments/{id}/paymentStatus/{Status}")
    public Payment updatePaymentStatus(@PathVariable Long id, @PathVariable String Status);

    @GetMapping("/reservations/{id}/reservationStatus/{Status}")
    public Reservation updateReservationStatus(@PathVariable Long id,@PathVariable String Status);
}