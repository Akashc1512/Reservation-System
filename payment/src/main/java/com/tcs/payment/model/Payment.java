package com.tcs.payment.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "payments")
@Data
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "CustomerId", nullable = false)
    private Long CustomerId;
    
    @Column(name = "Amount", nullable = false)
    private BigDecimal Amount;

    @Column(name = "Status")
    private String Status;
    
}
