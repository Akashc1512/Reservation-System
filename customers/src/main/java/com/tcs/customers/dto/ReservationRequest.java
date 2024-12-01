package com.tcs.customers.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ReservationRequest {

    private Long CustomerId;

    private Long HotelId;

    private BigDecimal BookingAmount;

    private String StartDate;

    private String EndDate;

}