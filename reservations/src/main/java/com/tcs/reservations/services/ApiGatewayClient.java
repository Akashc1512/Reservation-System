package com.tcs.reservations.services;

import com.tcs.reservations.dto.Hotels;
import com.tcs.reservations.dto.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "apigateway-service", url = "http://localhost:8888", path = "/")
public interface ApiGatewayClient {


    @GetMapping("/hotels/{HotelId}/bookingStatus/{Status}")
    public Hotels updateHotelBookingStatus(@PathVariable Long HotelId, @PathVariable String Status);

    @GetMapping("/hotels/{HotelId}")
    public Hotels getHotel(@PathVariable Long HotelId);

    @PostMapping("/payments")
    public Payment makePayment(@RequestBody Payment payment);

}