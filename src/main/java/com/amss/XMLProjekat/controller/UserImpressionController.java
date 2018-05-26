package com.amss.XMLProjekat.controller;

import java.util.Optional;

import org.modelmapper.ModelMapper;
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

import com.amss.XMLProjekat.beans.Accommodation;
import com.amss.XMLProjekat.beans.RegisteredUser;
import com.amss.XMLProjekat.beans.UserImpression;
import com.amss.XMLProjekat.dto.UserImpressionCreation;
import com.amss.XMLProjekat.dto.UserImpressionView;
import com.amss.XMLProjekat.repository.AccomodationRepo;
import com.amss.XMLProjekat.repository.RegisteredUserRepo;
import com.amss.XMLProjekat.repository.UserImpressionRepo;
@RestController
@RequestMapping(value="/userimpression")
public class UserImpressionController {

	@Autowired
	UserImpressionRepo repo;
	@Autowired
	AccomodationRepo accomodationRepo;
	@Autowired
	RegisteredUserRepo registeredUserRepo;
	@Autowired
	ModelMapper mapper;
	
	@PutMapping(value="/update",
			produces=MediaType.APPLICATION_JSON_UTF8_VALUE,
			consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> update(@RequestBody UserImpression newEntity) {
		if(repo.existsById(newEntity.getId())) {
			return new ResponseEntity<UserImpressionView>(mapper.map(repo.save(newEntity), UserImpressionView.class), HttpStatus.OK);
		}
		return new ResponseEntity<UserImpressionView>(HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping(value="/insert",
			produces=MediaType.APPLICATION_JSON_UTF8_VALUE,
			consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> insert(@RequestBody UserImpressionCreation newEntity) {
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
    	Integer ratingSum = accommodation.get().getUserImpressions().stream().mapToInt(i -> i.getRating()).sum();
    	if(ratingSum > 0) {
    		accommodation.get().setRating(ratingSum * 1.0 / accommodation.get().getUserImpressions().size());
    	}
		UserImpression userImpression = new UserImpression();
		userImpression.setComment(newEntity.getComment());
		userImpression.setRating(newEntity.getRating());
		userImpression.setVerified(false);
		userImpression.setRegisteredUser(user.get());
		userImpression.setAccommodation(accommodation.get());
		return new ResponseEntity<UserImpressionView>(mapper.map(repo.save(userImpression), UserImpressionView.class), HttpStatus.OK);
	}
	
}
