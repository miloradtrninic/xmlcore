package com.amss.XMLProjekat.controller;

import java.util.Date;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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
import com.amss.XMLProjekat.beans.User;
import com.amss.XMLProjekat.dto.MessageCreation;
import com.amss.XMLProjekat.dto.MessageView;
import com.amss.XMLProjekat.repository.MessageRepo;
import com.amss.XMLProjekat.repository.RegisteredUserRepo;
import com.amss.XMLProjekat.repository.ReservationRepo;
import com.amss.XMLProjekat.repository.UserRepo;

@RestController
@RequestMapping(value="message")
public class MessageController {

	@Autowired
	MessageRepo messageRepo;
	@Autowired
	RegisteredUserRepo registeredUserRepo;
	@Autowired
	UserRepo userRepo;
	@Autowired
	ModelMapper mapper;
	@Autowired
	ReservationRepo reservationRepo;
	
	@GetMapping(produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Secured({"ROLE_agent", "ROLE_registered"})
	public ResponseEntity<?> get(@PathVariable("reservationId") Long reservationId){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String username = authentication.getName();
    	Optional<RegisteredUser> user = registeredUserRepo.findOneByUsername(username);
    	if(!user.isPresent()) {
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	}
		return new ResponseEntity<>(mapper.map(messageRepo.findByReservationId(reservationId), MessageView.class), HttpStatus.OK);
	}
	@GetMapping(value="inbox", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Secured({"ROLE_agent", "ROLE_registered"})
	public ResponseEntity<?> getInbox(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String username = authentication.getName();
    	Optional<RegisteredUser> user = registeredUserRepo.findOneByUsername(username);
    	if(!user.isPresent()) {
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	}
		return new ResponseEntity<>(mapper.map(messageRepo.findByToUserId(user.get().getId()), MessageView.class), HttpStatus.OK);
	}
	@GetMapping(value="outbox", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Secured({"ROLE_agent", "ROLE_registered"})
	public ResponseEntity<?> getOutbox(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String username = authentication.getName();
    	Optional<RegisteredUser> user = registeredUserRepo.findOneByUsername(username);
    	if(!user.isPresent()) {
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	}
		return new ResponseEntity<>(mapper.map(messageRepo.findByFromUserId(user.get().getId()), MessageView.class), HttpStatus.OK);
	}
	
	@PostMapping(value="/insert",
			produces=MediaType.APPLICATION_JSON_UTF8_VALUE,
			consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Secured({"ROLE_agent", "ROLE_registered"})
	public ResponseEntity<?> insert(@RequestBody MessageCreation newEntity) {
		Optional<Reservation> reservation = reservationRepo.findById(newEntity.getReservationId());
		if(!reservation.isPresent()) {
			return new ResponseEntity<>("Reservation " + newEntity.getReservationId()+ " not found.",HttpStatus.BAD_REQUEST);
		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String username = authentication.getName();
    	Optional<User> fromUser = userRepo.findOneByUsername(username);
    	if(!fromUser.isPresent()) {
    		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    	}
    	Optional<User> toUser = userRepo.findOneByUsername(newEntity.getToUserUsername());
    	if(!toUser.isPresent()) {
    		return new ResponseEntity<>("Recepient "+ newEntity.getToUserUsername()+ " not found.",HttpStatus.BAD_REQUEST);
    	}
		Message message = new Message();
		message.setContent(newEntity.getContent());
		message.setFromUser(fromUser.get());
		message.setDateSent(new Date());
		message.setReservation(reservation.get());
		message.setToUser(toUser.get());
		return new ResponseEntity<>(mapper.map(messageRepo.save(message), MessageView.class), HttpStatus.OK);
	}
	
}
