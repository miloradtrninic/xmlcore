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
	
	@RequestMapping(value="/all", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Page<UserView> getAll(@NotNull final Pageable p) {
		return agentRepo.findAll(p).map(u -> mapper.map(u, UserView.class));
	}
	
	@PostMapping(value="/insert",
			produces=MediaType.APPLICATION_JSON_UTF8_VALUE,
			consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<AgentView> insert(@RequestBody AgentCreation newEntity) {
		Agent newAgent = mapper.map(newEntity, Agent.class);
		newAgent.setBlocked(false);
		newAgent.setRegistrationDate(new Date());
		return new ResponseEntity<AgentView>(mapper.map(agentRepo.save(newAgent), AgentView.class), HttpStatus.OK);
	}
	
	@PutMapping(value="/update",
			produces=MediaType.APPLICATION_JSON_UTF8_VALUE,
			consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> update(@RequestBody AgentView newEntity) {
		if(agentRepo.existsById(newEntity.getId())) {
			Agent agent = mapper.map(newEntity, Agent.class);
			return new ResponseEntity<UserView>(mapper.map(agentRepo.save(agent), AgentView.class), HttpStatus.OK);
		}
		return new ResponseEntity<UserView>(HttpStatus.BAD_REQUEST);
	}
	
	
	@DeleteMapping(value="/delete",
			produces=MediaType.APPLICATION_JSON_UTF8_VALUE,
			consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> delete(@RequestParam("id") Long id) {
		Optional<Agent> entity = agentRepo.findById(id);
		if(entity.isPresent()) {
			agentRepo.delete(entity.get());
			return new ResponseEntity<UserView>(mapper.map(entity.get(), AgentView.class), HttpStatus.OK);
		}
		return new ResponseEntity<AgentView>(HttpStatus.BAD_REQUEST);
	}
	
}
