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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amss.XMLProjekat.beans.Agent;
import com.amss.XMLProjekat.beans.User;
import com.amss.XMLProjekat.dto.AgentCreation;
import com.amss.XMLProjekat.dto.AgentView;
import com.amss.XMLProjekat.dto.UserCreation;
import com.amss.XMLProjekat.dto.UserView;
import com.amss.XMLProjekat.repository.AgentRepo;

@RestController
@RequestMapping(value="/agents")
public class AgentController {

	@Autowired
	AgentRepo agentRepo;
	@Autowired
	ModelMapper mapper;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@RequestMapping(value="/all", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Page<AgentView> getAll(@NotNull final Pageable p) {
		return agentRepo.findAll(p).map(u -> mapper.map(u, AgentView.class));
	}
	
	@PostMapping(value="/insert",
			produces=MediaType.APPLICATION_JSON_UTF8_VALUE,
			consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<AgentView> insert(@RequestBody AgentCreation newEntity) {
		Agent newAgent = mapper.map(newEntity, Agent.class);
		newAgent.setPassword(passwordEncoder.encode(newEntity.getPassword()));
		newAgent.setBlocked(false);
		newAgent.setRegistrationDate(new Date());
		return new ResponseEntity<AgentView>(mapper.map(agentRepo.save(newAgent), AgentView.class), HttpStatus.OK);
	}
	
	@PutMapping(value="/update",
			produces=MediaType.APPLICATION_JSON_UTF8_VALUE,
			consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> update(@RequestBody AgentView newEntity) {
		Optional<Agent> entity = agentRepo.findById(newEntity.getId());
		if(entity.isPresent()) {
			Agent agent = entity.get();
			agent.setBlocked(newEntity.getBlocked());
			agent.setEmail(newEntity.getEmail());
			agent.setFirstName(newEntity.getFirstName());
			agent.setLastName(newEntity.getLastName());
			agent.setPib(newEntity.getPib());
			return new ResponseEntity<UserView>(mapper.map(agentRepo.save(agent), AgentView.class), HttpStatus.OK);
		}
		
		return new ResponseEntity<UserView>(HttpStatus.BAD_REQUEST);
	}
	
	
	@DeleteMapping(value="/delete",
			produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> delete(@RequestParam("id") Long id) {
		Optional<Agent> entity = agentRepo.findById(id);
		if(entity.isPresent()) {
			agentRepo.delete(entity.get());
			return new ResponseEntity<UserView>(mapper.map(entity.get(), AgentView.class), HttpStatus.OK);
		}
		return new ResponseEntity<AgentView>(HttpStatus.BAD_REQUEST);
	}
	
}
