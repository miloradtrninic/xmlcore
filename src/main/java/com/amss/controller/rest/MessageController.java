package com.amss.controller.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amss.beans.Message;
import com.amss.beans.RegisteredUser;
import com.amss.repository.MessageRepo;
import com.amss.repository.RegisteredUserRepo;

@RestController
@RequestMapping(value="message")
public class MessageController {

	@Autowired
	MessageRepo messageRepo;
	@Autowired
	RegisteredUserRepo registeredUserRepo;
	
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
	
}
