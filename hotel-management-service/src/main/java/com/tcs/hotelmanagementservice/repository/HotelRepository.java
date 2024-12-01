package com.tcs.hotelmanagementservice.repository;

import com.tcs.hotelmanagementservice.entities.Hotels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface HotelRepository extends JpaRepository<Hotels, Long>{

	
}
