package com.tcs.customers.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tcs.customers.dto.Hotels;
import com.tcs.customers.dto.Notification;
import com.tcs.customers.dto.ReservationRequest;
import com.tcs.customers.entities.Customers;
import com.tcs.customers.entities.Reservation;
import com.tcs.customers.errorhandling.ResourceNotFoundException;
import com.tcs.customers.repositories.CustomersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CustomersService {

    @Autowired
    private CustomersRepository customersRepository;

    @Autowired
    private KafkaService kafkaService;

    @Autowired
    private ApiGatewayClient apiGatewayClient;

    public Customers createCustomer(Customers customers) throws Exception {
        Optional<Customers> existCustomer = customersRepository.findByEmail(customers.getEmail());
        if (!existCustomer.isPresent()) {
            Customers createdCustomer = customersRepository.save(customers);
            if (Objects.nonNull(createdCustomer)) {
                kafkaService.sendNotification(Notification.builder()
                        .CustomerId(createdCustomer.getId())
                        .eventType("customer registered")
                        .message("Welcome To Hotel Reservation")
                        .build());
            }

            return createdCustomer;
        } else {
            throw new Exception("Customer with email " + customers.getEmail() + " is Already Exists.");
        }
    }

    public Customers fetchCustomerById(Long CustomerId) throws Exception {
        Optional<Customers> customersOptional = customersRepository.findById(CustomerId);
        if (customersOptional.isPresent()) {
            return customersOptional.get();
        } else {
            throw new ResourceNotFoundException("Customer Not Found");
        }
    }

    public List<Customers> fetchAllCustomers() {
        return customersRepository.findAll();
    }

    public Customers updateCustomer(Customers customers) throws Exception {
        Optional<Customers> customersOptional = customersRepository.findById(customers.getId());
        if (customersOptional.isPresent()) {
            return customersRepository.save(customers);
        } else {
            throw new ResourceNotFoundException("Customer is not Exists.");
        }
    }


    public String deleteCustomerById(Long CustomerId) {
        Optional<Customers> customersOptional = customersRepository.findById(CustomerId);
        if (customersOptional.isPresent()) {
            customersRepository.deleteById(CustomerId);
            return "Success";
        } else {
            throw new ResourceNotFoundException("Customer Not Found");
        }
    }

    // Customer Initiate For Booking Hotel
    public String makeReservation(ReservationRequest reservation) throws Exception {
        Optional<Customers> customerOp = customersRepository.findById(reservation.getCustomerId());
        if (customerOp.isPresent()) {
            Customers customer = customerOp.get();
            Hotels hotel = apiGatewayClient.getHotel(reservation.getHotelId());
            if (hotel != null && "AVAILABLE".equalsIgnoreCase(hotel.getStatus())) {
                // Send Kafka Message TO Book Hotel For Customer
                // Sending Message to Reservation Service
                kafkaService.sendBookingNotification(ReservationRequest.builder()
                        .CustomerId(customer.getId())
                        .BookingAmount(reservation.getBookingAmount())
                        .StartDate(reservation.getStartDate())
                        .EndDate(reservation.getEndDate())
                        .HotelId(hotel.getId())
                        .build());
                return "Your Hotel Booking is in progress";
            } else {
                // Send Notification as Hotel is Not Available for Booking
                kafkaService.sendNotification(Notification.builder()
                        .CustomerId(customer.getId())
                        .eventType("hotel")
                        .message("Currently Hotel not available for booking")
                        .build());
                return "Currently Hotel is not available for booking";
            }
        } else {
            throw new ResourceNotFoundException("Customer is not Exists.");
        }
    }

    // Customer Cancel Hotel Booking
    // Updating Hotel Status to AVAILABLE
    // Reservation Status to CANCELLED
    // Payment Status to REFUND
    // Send Notification to Customer for Cancelled Reservation
    public String cancelReservation(Long reservationId) throws Exception {

        Reservation reservation = apiGatewayClient.getReservationById(reservationId);
        apiGatewayClient.updateHotelBookingStatus(reservation.getHotelId(), "AVAILABLE");
        apiGatewayClient.updatePaymentStatus(reservation.getPaymentId(), "REFUND");
        apiGatewayClient.updateReservationStatus(reservationId, "CANCELLED");

        // Send Notification as Reservation is Cancelled
        kafkaService.sendNotification(Notification.builder()
                .CustomerId(reservation.getCustomerId())
                .eventType("reservation")
                .message("Customer Reservation for Hotel is Cancelled, Reservation ID :: " + reservationId)
                .build());
        return "Customer Reservation for Hotel is Cancelled";

    }

}
