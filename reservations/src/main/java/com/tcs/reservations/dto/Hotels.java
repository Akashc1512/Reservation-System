package com.tcs.reservations.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
public class Hotels {

    private Long id;

    private String name;

    private String description;

    private String Status;

}