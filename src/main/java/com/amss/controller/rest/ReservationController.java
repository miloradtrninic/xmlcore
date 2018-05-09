package com.amss.controller.rest;

import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	
	@GetMapping(value="myreservations", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> getMy(@NotNull Pageable p) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String username = authentication.getName();
    	Optional<RegisteredUser> user = registeredUserRepo.findOneByUsername(username);
    	if(!user.isPresent()) {
    		return new ResponseEntity<Reservation>(HttpStatus.BAD_REQUEST);
    	}
    	return new ResponseEntity<Page<Reservation>>(reservationRepo.findByRegisteredUserId(user.get().getId()), HttpStatus.OK);
	}
	
	@PostMapping(value="reserve", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
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
    	return new ResponseEntity<Reservation>(reservationRepo.save(reservation), HttpStatus.OK);
	}
	
	@DeleteMapping(value="/cancel",
			produces=MediaType.APPLICATION_JSON_UTF8_VALUE,
			consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Reservation> delete(@PathVariable("id") Long id) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String username = authentication.getName();
    	Optional<RegisteredUser> user = registeredUserRepo.findOneByUsername(username);
    	if(!user.isPresent()) {
    		return new ResponseEntity<Reservation>(HttpStatus.BAD_REQUEST);
    	}
		Optional<Reservation> entity = reservationRepo.findOneByIdAndRegisteredUserId(id, user.get().getId());
		if(entity.isPresent()) {
			reservationRepo.delete(entity.get());
			return new ResponseEntity<Reservation>(entity.get(), HttpStatus.OK);
		}
		return new ResponseEntity<Reservation>(HttpStatus.BAD_REQUEST);
	}
}
