package com.amss.XMLProjekat.controller;

import java.util.Date;
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

import com.amss.XMLProjekat.beans.User;
import com.amss.XMLProjekat.dto.UserView;
import com.amss.XMLProjekat.repository.AgentRepo;
import com.amss.XMLProjekat.repository.UserRepo;

@RestController
@RequestMapping(value="/users")
public class UserController {
	@Autowired
	UserRepo repo;
	@Autowired
	AgentRepo agentRepo;
	@Autowired
	ModelMapper mapper;
	
	@RequestMapping(value="/all", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Secured({"ROLE_admin"})
	public Page<UserView> getAll(@NotNull final Pageable p) {
		return repo.findAll(p).map(u -> mapper.map(u, UserView.class));
	}
	
	@PutMapping(value="/update",
			produces=MediaType.APPLICATION_JSON_UTF8_VALUE,
			consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Secured({"ROLE_admin"})
	public ResponseEntity<?> update(@RequestBody User newEntity) {
		if(repo.existsById(newEntity.getId())) {
			return new ResponseEntity<UserView>(mapper.map(repo.save(newEntity), UserView.class), HttpStatus.OK);
		}
		return new ResponseEntity<UserView>(HttpStatus.BAD_REQUEST);
	}
	
	
	@DeleteMapping(value="/delete",
			produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Secured({"ROLE_admin"})
	public ResponseEntity<?> delete(@RequestParam("id") Long id) {
		Optional<User> entity = repo.findById(id);
		if(entity.isPresent()) {
			repo.delete(entity.get());
			return new ResponseEntity<UserView>(mapper.map(entity.get(), UserView.class), HttpStatus.OK);
		}
		return new ResponseEntity<UserView>(HttpStatus.BAD_REQUEST);
	}
	
}
