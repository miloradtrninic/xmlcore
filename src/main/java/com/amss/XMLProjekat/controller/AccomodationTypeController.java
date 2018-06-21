package com.amss.XMLProjekat.controller;

import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amss.XMLProjekat.beans.AccommodationType;
import com.amss.XMLProjekat.dto.AccommodationTypeView;
import com.amss.XMLProjekat.repository.AccommodationTypeRepo;

@RestController
@RequestMapping(value="/type")
public class AccomodationTypeController {
	
	@Autowired
	AccommodationTypeRepo accommodationTypeRepo;
	
	@Autowired
	ModelMapper mapper;
	
	@GetMapping(value="/all", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Secured({"ROLE_ANONYMOUS", "ROLE_agent", "ROLE_admin", "ROLE_registered"})
	public Page<AccommodationTypeView> getAll(@NotNull final Pageable p) {
		return accommodationTypeRepo.findAll(p).map(a -> mapper.map(a, AccommodationTypeView.class));
	}
	
	@PutMapping(value="/update",
			produces=MediaType.APPLICATION_JSON_UTF8_VALUE,
			consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Secured({"ROLE_admin"})
	public ResponseEntity<AccommodationType> update(@RequestBody AccommodationType accTypeNew) {
		if(accommodationTypeRepo.existsById(accTypeNew.getId())) {
			AccommodationType updated = accommodationTypeRepo.save(accTypeNew);
			return new ResponseEntity<AccommodationType>(updated, HttpStatus.OK);
		}
		return new ResponseEntity<AccommodationType>(HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping(value="/insert",
			produces=MediaType.APPLICATION_JSON_UTF8_VALUE,
			consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Secured({"ROLE_admin"})
	public ResponseEntity<AccommodationType> insert(@RequestBody AccommodationType accTypeNew) {
		return new ResponseEntity<AccommodationType>(accommodationTypeRepo.save(accTypeNew), HttpStatus.OK);
	}
	
	@DeleteMapping(value="/delete",
			produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Secured({"ROLE_admin"})
	public ResponseEntity<AccommodationType> delete(@RequestParam("id") Long id) {
		Optional<AccommodationType> accType = accommodationTypeRepo.findById(id);
		if(accType.isPresent()) {
			accommodationTypeRepo.delete(accType.get());
			return new ResponseEntity<AccommodationType>(accType.get(), HttpStatus.OK);
		}
		return new ResponseEntity<AccommodationType>(HttpStatus.BAD_REQUEST);
	}
	
}
