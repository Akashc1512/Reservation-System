package com.tcs.reservations.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationRequest {

    private Long CustomerId;

    private Long HotelId;

    private BigDecimal BookingAmount;

    private String StartDate;

    private String EndDate;

}