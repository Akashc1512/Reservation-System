package com.tcs.reservations.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Notification {

    private Long id;

    private String eventType;

    private Long CustomerId;

    private String message;

    private String createdOn;
}