package com.amss.controller.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amss.beans.Accommodation;
import com.amss.beans.RegisteredUser;
import com.amss.beans.Reservation;
import com.amss.dto.ReservationCreation;
import com.amss.repository.AccomodationRepo;
import com.amss.repository.RegisteredUserRepo;
import com.amss.repository.ReservationRepo;

@RestController
@RequestMapping(value="/reservation")
public class ReservationController {
	@Autowired
	ReservationRepo reservationRepo;
	
	@Autowired
	RegisteredUserRepo registeredUserRepo;
	
	@Autowired
	AccomodationRepo accomodationRepo;
	
	public ResponseEntity<?> reserve(ReservationCreation reserve) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String username = authentication.getName();
    	Optional<RegisteredUser> user = registeredUserRepo.findOneByUsername(username);
    	if(!user.isPresent()) {
    		return new ResponseEntity<Reservation>(HttpStatus.BAD_REQUEST);
    	}
    	Optional<Accommodation> accommodation = accomodationRepo.findById(reserve.getAccommodationId());
    	if(!accommodation.isPresent()) {
    		return new ResponseEntity<Reservation>(HttpStatus.BAD_REQUEST);
    	}
    	Reservation reservation = new Reservation();
    	reservation.setConfirmed(false);
    	reservation.setEndingDate(reserve.getEndingDate());
    	reservation.setStartingDate(reservation.getStartingDate());
    	reservation.setRegisteredUser(user.get());
    	reservation.setAccommodation(accommodation.get());
    	return new ResponseEntity<Reservation>(reservationRepo.save(reservation), HttpStatus.BAD_REQUEST);
	}
}
