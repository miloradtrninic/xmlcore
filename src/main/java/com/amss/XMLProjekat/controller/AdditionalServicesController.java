package com.amss.XMLProjekat.controller;

import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amss.XMLProjekat.beans.AdditionalService;
import com.amss.XMLProjekat.repository.AdditionalServiceRepo;

@RestController
@RequestMapping(value="/additionalservices")
public class AdditionalServicesController {
	@Autowired
	AdditionalServiceRepo repo;
	
	@RequestMapping(value="/all", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Secured({"ROLE_ANONYMOUS", "ROLE_agent", "ROLE_admin", "ROLE_registered"})
	public Page<AdditionalService> getAll(@NotNull final Pageable p) {
		return repo.findAll(p);
	}
	
	@PutMapping(value="/update",
			produces=MediaType.APPLICATION_JSON_UTF8_VALUE,
			consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Secured({"ROLE_admin"})
	public ResponseEntity<AdditionalService> update(@RequestBody AdditionalService newEntity) {
		if(repo.existsById(newEntity.getId())) {
			return new ResponseEntity<AdditionalService>(repo.save(newEntity), HttpStatus.OK);
		}
		return new ResponseEntity<AdditionalService>(HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping(value="/insert",
			produces=MediaType.APPLICATION_JSON_UTF8_VALUE,
			consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Secured({"ROLE_admin"})
	public ResponseEntity<AdditionalService> insert(@RequestBody AdditionalService newEntity) {
		return new ResponseEntity<AdditionalService>(repo.save(newEntity), HttpStatus.OK);
	}
	
	@DeleteMapping(value="/delete",
			produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Secured({"ROLE_admin"})
	public ResponseEntity<AdditionalService> delete(@RequestParam("id") Long id) {
		Optional<AdditionalService> entity = repo.findById(id);
		if(entity.isPresent()) {
			repo.delete(entity.get());
			return new ResponseEntity<AdditionalService>(entity.get(), HttpStatus.OK);
		}
		return new ResponseEntity<AdditionalService>(HttpStatus.BAD_REQUEST);
	}
}
