package com.amss.XMLProjekat.controller;

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

import com.amss.XMLProjekat.beans.Category;
import com.amss.XMLProjekat.repository.CategoryRepo;

import java.util.Optional;
@RestController
@RequestMapping(value="/category")
public class CategoryController {

	@Autowired
	CategoryRepo categoryRepo;
	@Autowired
	ModelMapper mapper;
	
	@RequestMapping(value="/all", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Page<Category> getAll(@NotNull final Pageable p) {
		return categoryRepo.findAll(p);
	}
	
	@PutMapping(value="/update",
			produces=MediaType.APPLICATION_JSON_UTF8_VALUE,
			consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> update(@RequestBody Category newCat) {
		if(categoryRepo.existsById(newCat.getId())) {
			Category updated = categoryRepo.save(newCat);
			return new ResponseEntity<>(updated, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping(value="/insert",
			produces=MediaType.APPLICATION_JSON_UTF8_VALUE,
			consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Category> insert(@RequestBody Category newCat) {
		return new ResponseEntity<Category>(categoryRepo.save(newCat), HttpStatus.OK);
	}
	
	@DeleteMapping(value="/delete",
			produces=MediaType.APPLICATION_JSON_UTF8_VALUE,
			consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Category> delete(@PathVariable("id") Long id) {
		Optional<Category> accType = categoryRepo.findById(id);
		if(accType.isPresent()) {
			categoryRepo.delete(accType.get());
			return new ResponseEntity<Category>(accType.get(), HttpStatus.OK);
		}
		return new ResponseEntity<Category>(HttpStatus.BAD_REQUEST);
	}
}
