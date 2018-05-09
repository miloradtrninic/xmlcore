package com.amss.controller.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amss.beans.Accommodation;
import com.amss.beans.RegisteredUser;
import com.amss.beans.UserImpression;
import com.amss.dto.UserImpressionCreation;
import com.amss.repository.AccomodationRepo;
import com.amss.repository.RegisteredUserRepo;
import com.amss.repository.UserImpressionRepo;

@RestController
@RequestMapping(value="/userimpression")
public class UserImpressionController {

	@Autowired
	UserImpressionRepo repo;
	@Autowired
	AccomodationRepo accomodationRepo;
	@Autowired
	RegisteredUserRepo registeredUserRepo;
	
	// TODO PREGLED OCENA PO APARTMANU
	
	@PutMapping(value="/update",
			produces=MediaType.APPLICATION_JSON_UTF8_VALUE,
			consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<UserImpression> update(@RequestBody UserImpression newEntity) {
		if(repo.existsById(newEntity.getId())) {
			return new ResponseEntity<UserImpression>(repo.save(newEntity), HttpStatus.OK);
		}
		return new ResponseEntity<UserImpression>(HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping(value="/insert",
			produces=MediaType.APPLICATION_JSON_UTF8_VALUE,
			consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<UserImpression> insert(@RequestBody UserImpressionCreation newEntity) {
		Optional<Accommodation> accommodation = accomodationRepo.findById(newEntity.getAccommodationId());
		if(!accommodation.isPresent()) {
			return new ResponseEntity<UserImpression>(HttpStatus.BAD_REQUEST);
		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String username = authentication.getName();
    	Optional<RegisteredUser> user = registeredUserRepo.findOneByUsername(username);
    	if(!user.isPresent()) {
    		return new ResponseEntity<UserImpression>(HttpStatus.BAD_REQUEST);
    	}
		UserImpression userImpression = new UserImpression();
		userImpression.setComment(newEntity.getComment());
		userImpression.setRating(newEntity.getRating());
		userImpression.setVerified(false);
		userImpression.setRegisteredUser(user.get());
		userImpression.setAccommodation(accommodation.get());
		return new ResponseEntity<UserImpression>(repo.save(userImpression), HttpStatus.OK);
	}
	
}
