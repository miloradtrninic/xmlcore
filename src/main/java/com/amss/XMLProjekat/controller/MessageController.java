package com.amss.XMLProjekat.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amss.XMLProjekat.beans.Message;
import com.amss.XMLProjekat.beans.RegisteredUser;
import com.amss.XMLProjekat.beans.Reservation;
import com.amss.XMLProjekat.dto.MessageCreation;
import com.amss.XMLProjekat.repository.MessageRepo;
import com.amss.XMLProjekat.repository.RegisteredUserRepo;
import com.amss.XMLProjekat.repository.ReservationRepo;

@RestController
@RequestMapping(value="message")
public class MessageController {

	@Autowired
	MessageRepo messageRepo;
	@Autowired
	RegisteredUserRepo registeredUserRepo;
	
	@Autowired
	ReservationRepo reservationRepo;
	
	@GetMapping(produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> get(@PathVariable("reservationId") Long reservationId){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String username = authentication.getName();
    	Optional<RegisteredUser> user = registeredUserRepo.findOneByUsername(username);
    	if(!user.isPresent()) {
    		return new ResponseEntity<Message>(HttpStatus.BAD_REQUEST);
    	}
		return new ResponseEntity<>(messageRepo.findByReservationId(reservationId), HttpStatus.OK);
	}
	@GetMapping(value="inbox", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> getInbox(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String username = authentication.getName();
    	Optional<RegisteredUser> user = registeredUserRepo.findOneByUsername(username);
    	if(!user.isPresent()) {
    		return new ResponseEntity<Message>(HttpStatus.BAD_REQUEST);
    	}
		return new ResponseEntity<>(messageRepo.findByToUserId(user.get().getId()), HttpStatus.OK);
	}
	@GetMapping(value="outbox", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> getOutbox(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String username = authentication.getName();
    	Optional<RegisteredUser> user = registeredUserRepo.findOneByUsername(username);
    	if(!user.isPresent()) {
    		return new ResponseEntity<Message>(HttpStatus.BAD_REQUEST);
    	}
		return new ResponseEntity<>(messageRepo.findByFromUserId(user.get().getId()), HttpStatus.OK);
	}
	
	@PostMapping(value="/insert",
			produces=MediaType.APPLICATION_JSON_UTF8_VALUE,
			consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Message> insert(@RequestBody MessageCreation newEntity) {
		Optional<Reservation> reservation = reservationRepo.findById(newEntity.getReservationId());
		if(!reservation.isPresent()) {
			return new ResponseEntity<Message>(HttpStatus.BAD_REQUEST);
		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String username = authentication.getName();
    	Optional<RegisteredUser> user = registeredUserRepo.findOneByUsername(username);
    	if(!user.isPresent()) {
    		return new ResponseEntity<Message>(HttpStatus.BAD_REQUEST);
    	}
    	Optional<RegisteredUser> toUser = registeredUserRepo.findById(newEntity.getToUserId());
    	if(!toUser.isPresent()) {
    		return new ResponseEntity<Message>(HttpStatus.BAD_REQUEST);
    	}
		Message message = new Message();
		message.setContent(newEntity.getContent());
		message.setFromUser(user.get());
		message.setReservation(reservation.get());
		message.setToUser(toUser.get());
		return new ResponseEntity<Message>(messageRepo.save(message), HttpStatus.OK);
	}
	
}
