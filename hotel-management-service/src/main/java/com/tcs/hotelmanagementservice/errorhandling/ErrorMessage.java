package com.tcs.hotelmanagementservice.errorhandling;

import lombok.Data;

import java.util.Date;

@Data
public class ErrorMessage {
  private int StatusCode;
  private Date timestamp;
  private String message;
  private String description;

  public ErrorMessage(int StatusCode, Date timestamp, String message, String description) {
    this.StatusCode = StatusCode;
    this.timestamp = timestamp;
    this.message = message;
    this.description = description;
  }
}