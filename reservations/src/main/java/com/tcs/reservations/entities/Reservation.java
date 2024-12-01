package com.tcs.reservations.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "reservations")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "CustomerId")
    private Long CustomerId;

    @Column(name = "HotelId")
    private Long HotelId;

    @Column(name = "PaymentId")
    private Long PaymentId;

    @Column(name = "BookingAmount")
    private BigDecimal BookingAmount;

    @Column(name = "StartDate")
    private String StartDate;

    @Column(name = "EndDate")
    private String EndDate;

    @Column(name = "Status")
    private String Status;

}