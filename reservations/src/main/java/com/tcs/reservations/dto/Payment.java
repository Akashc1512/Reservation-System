package com.tcs.reservations.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Payment {
    private Long id;
    
    private Long CustomerId;
    
    private BigDecimal Amount;

    private String Status;
}
