package com.tcs.hotelmanagementservice.services;

import com.tcs.hotelmanagementservice.entities.Hotels;
import com.tcs.hotelmanagementservice.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService {

	@Autowired
	HotelRepository hotelRepository;
	
	public List<Hotels> getAllHotel(){
		return hotelRepository.findAll();
	}
	
	public Hotels addHotel(Hotels hotel) {
		hotel.setStatus("AVAILABLE");
		return hotelRepository.save(hotel);
	}
	
	public Hotels getHotel(Long HotelId) {
		return hotelRepository.findById(HotelId).orElse(null);
	}
	
	public Hotels updateHotelBookingStatus(Long HotelId, String Status) {
		Hotels hotel =  getHotel(HotelId);
		if(hotel!=null) {
			hotel.setStatus(Status);
			return hotelRepository.save(hotel);
		} else {
			return hotel;
		}
	}
}
