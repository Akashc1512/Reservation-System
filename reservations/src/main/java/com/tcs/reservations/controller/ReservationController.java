package com.tcs.reservations.controller;

import com.tcs.reservations.entities.Reservation;
import com.tcs.reservations.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationRepository reservationRepository;

    @GetMapping("/")
    public List<Reservation> getReservations() {
		return reservationRepository.findAll();
    }

    @GetMapping("/{id}")
    public Reservation getReservationById(@PathVariable Long id) {
        return reservationRepository.findById(id).get();
    }

    @GetMapping("/{id}/reservationStatus/{Status}")
    public Reservation updateReservationStatus(@PathVariable Long id,@PathVariable String Status){
        Reservation reservation =  reservationRepository.findById(id).orElse(null);
        if(Objects.nonNull(reservation)) {
            reservation.setStatus(Status);
            return reservationRepository.save(reservation);
        } else {
            return reservation;
        }
    }
}
