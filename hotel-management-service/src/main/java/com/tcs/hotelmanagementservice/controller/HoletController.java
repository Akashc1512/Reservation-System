package com.tcs.hotelmanagementservice.controller;

import com.tcs.hotelmanagementservice.entities.Hotels;
import com.tcs.hotelmanagementservice.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;



@RestController
@RequestMapping("/hotels")
public class HoletController {

	@Autowired
	HotelService hotelService;
	
	
	@GetMapping("/")
	public List<Hotels> getAllHotel(){
		return hotelService.getAllHotel();
	}
	
	@PostMapping("/addHotel")
	public Hotels addHotel(@RequestBody Hotels hotel) {
		return hotelService.addHotel(hotel);
		
	}
	@GetMapping("/{HotelId}")
	public Hotels getHotel(@PathVariable Long HotelId){
		return hotelService.getHotel(HotelId);
		
	}
	
	@GetMapping("/{HotelId}/bookingStatus/{Status}")
	public Hotels updateHotelBookingStatus(@PathVariable Long HotelId,@PathVariable String Status){
		return hotelService.updateHotelBookingStatus(HotelId,Status);
	}
}
