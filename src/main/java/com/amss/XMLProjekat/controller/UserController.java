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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amss.XMLProjekat.beans.Agent;
import com.amss.XMLProjekat.beans.User;
import com.amss.XMLProjekat.dto.AgentCreation;
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
	public Page<User> getAll(@NotNull final Pageable p) {
		return repo.findAll(p);
	}
	
	@PutMapping(value="/update",
			produces=MediaType.APPLICATION_JSON_UTF8_VALUE,
			consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<User> update(@RequestBody User newEntity) {
		if(repo.existsById(newEntity.getId())) {
			return new ResponseEntity<User>(repo.save(newEntity), HttpStatus.OK);
		}
		return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
	}
	@PostMapping(value="/insert",
			produces=MediaType.APPLICATION_JSON_UTF8_VALUE,
			consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Agent> insert(@RequestBody AgentCreation newEntity) {
		Agent newAgent = mapper.map(newEntity, Agent.class);
		newAgent.setRegistrationDate(new Date());
		return new ResponseEntity<Agent>(agentRepo.save(newAgent), HttpStatus.OK);
	}
	
	@DeleteMapping(value="/delete",
			produces=MediaType.APPLICATION_JSON_UTF8_VALUE,
			consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<User> delete(@PathVariable("id") Long id) {
		Optional<User> entity = repo.findById(id);
		if(entity.isPresent()) {
			repo.delete(entity.get());
			return new ResponseEntity<User>(entity.get(), HttpStatus.OK);
		}
		return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
	}
	
}
