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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amss.XMLProjekat.beans.RegisteredUser;
import com.amss.XMLProjekat.dto.RegisteredUserView;
import com.amss.XMLProjekat.dto.UserView;
import com.amss.XMLProjekat.repository.RegisteredUserRepo;

@RestController
@RequestMapping(value="/regularuser")
public class RegisteredUserController {
	@Autowired
	RegisteredUserRepo repo;
	@Autowired
	ModelMapper mapper;
	
	@RequestMapping(value="/all", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Secured({"ROLE_admin"})
	public Page<RegisteredUserView> getAll(@NotNull final Pageable p) {
		return repo.findAll(p).map(u -> mapper.map(u, RegisteredUserView.class));
	}
	
	@PutMapping(value="/update",
			produces=MediaType.APPLICATION_JSON_UTF8_VALUE,
			consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Secured({"ROLE_admin"})
	public ResponseEntity<?> update(@RequestBody RegisteredUserView newEntity) {
		Optional<RegisteredUser> entity = repo.findById(newEntity.getId());
		if(entity.isPresent()) {
			RegisteredUser user = entity.get();
			user.setBlocked(newEntity.getBlocked());
			user.setEmail(newEntity.getEmail());
			user.setFirstName(newEntity.getFirstName());
			user.setLastName(newEntity.getLastName());
			return new ResponseEntity<RegisteredUserView>(mapper.map(repo.save(user), RegisteredUserView.class), HttpStatus.OK);
		}
		return new ResponseEntity<RegisteredUserView>(HttpStatus.BAD_REQUEST);
	}
	
	
	@DeleteMapping(value="/delete",
			produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Secured({"ROLE_admin"})
	public ResponseEntity<?> delete(@RequestParam("id") Long id) {
		Optional<RegisteredUser> entity = repo.findById(id);
		if(entity.isPresent()) {
			repo.delete(entity.get());
			return new ResponseEntity<RegisteredUserView>(mapper.map(entity.get(), RegisteredUserView.class), HttpStatus.OK);
		}
		return new ResponseEntity<RegisteredUserView>(HttpStatus.BAD_REQUEST);
	}
	
}
