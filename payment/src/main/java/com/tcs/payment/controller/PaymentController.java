package com.tcs.payment.controller;

import com.tcs.payment.model.Payment;
import com.tcs.payment.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/payments")
public class PaymentController {

	@Autowired
    PaymentRepository paymentRepository;

    @PostMapping
    public Payment makePayment(@RequestBody Payment payment) {
		return paymentRepository.save(payment);
    }

    @GetMapping("/{id}")
    public List<Payment> getPaymentById(@PathVariable Long id) {
		return paymentRepository.findByCustomerId(id);
    }

    @GetMapping("/{id}/paymentStatus/{Status}")
    public Payment updatePaymentStatus(@PathVariable Long id,@PathVariable String Status){
        Payment payment =  paymentRepository.findById(id).orElse(null);
        if(Objects.nonNull(payment)) {
            payment.setStatus(Status);
            return paymentRepository.save(payment);
        } else {
            return payment;
        }
    }
}
