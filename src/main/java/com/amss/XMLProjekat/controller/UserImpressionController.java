package com.amss.XMLProjekat.controller;

import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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
import com.amss.XMLProjekat.dto.AgentView;
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
	
	@RequestMapping(value="/all", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Secured({"ROLE_admin"})
	public Page<UserImpressionView> getAll(@NotNull final Pageable p) {
		return repo.findAll(p).map(u -> mapper.map(u, UserImpressionView.class));
	}
	
	@PutMapping(value="/update",
			produces=MediaType.APPLICATION_JSON_UTF8_VALUE,
			consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Secured({"ROLE_admin"})
	public ResponseEntity<?> update(@RequestBody UserImpressionView newEntity) {
		Optional<UserImpression> impression = repo.findById(newEntity.getId());
		if(impression.isPresent()) {
			impression.get().setVerified(newEntity.isVerified());
			return new ResponseEntity<UserImpressionView>(mapper.map(repo.save(impression.get()), UserImpressionView.class), HttpStatus.OK);
		}
		return new ResponseEntity<UserImpressionView>(HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping(value="/insert",
			produces=MediaType.APPLICATION_JSON_UTF8_VALUE,
			consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Secured({"ROLE_registered"})
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
