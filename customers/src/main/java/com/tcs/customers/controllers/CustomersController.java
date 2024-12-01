package com.tcs.customers.controllers;

import com.tcs.customers.dto.ReservationRequest;
import com.tcs.customers.entities.Customers;
import com.tcs.customers.services.CustomersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
public class CustomersController {

    @Autowired
    private CustomersService customersService;

    @PostMapping("/register")
    public ResponseEntity<Object> createCustomer(@RequestBody Customers customers) throws Exception{
        return ResponseEntity.ok(customersService.createCustomer(customers));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> fetchCustomerById(@PathVariable(value = "id") Long CustomerId) throws Exception{
        return ResponseEntity.ok(customersService.fetchCustomerById(CustomerId));
    }

    @GetMapping("/")
    public ResponseEntity<Object> fetchAllCustomers() throws Exception{
        return ResponseEntity.ok(customersService.fetchAllCustomers());
    }

    @PutMapping("/")
    public ResponseEntity<Object> updateCustomer(@RequestBody Customers customers) throws Exception{
        return ResponseEntity.ok(customersService.updateCustomer(customers));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCustomerById(@PathVariable(value = "id") Long CustomerId) throws Exception{
        return ResponseEntity.ok(customersService.deleteCustomerById(CustomerId));
    }

    @PostMapping("/makereservation")
    public ResponseEntity<Object> makeReservasion(@RequestBody ReservationRequest reservation) throws Exception{
        return ResponseEntity.ok(customersService.makeReservation(reservation));
    }

    @GetMapping("/cancelreservation/{id}")
    public ResponseEntity<Object> cancelReservasion(@PathVariable(value = "id") Long reservationId) throws Exception{
        return ResponseEntity.ok(customersService.cancelReservation(reservationId));
    }
}