package com.amss.XMLProjekat.controller;

import java.util.Optional;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.amss.XMLProjekat.beans.Accommodation;
import com.amss.XMLProjekat.dto.AccommodationView;
import com.amss.XMLProjekat.repository.AccomodationRepo;
import com.amss.XMLProjekat.repository.dsl.PredicateBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

@RestController
@RequestMapping("/accommodation")
public class AccommodationController {

	@Autowired
	ModelMapper mapper;
	
	@Autowired
	AccomodationRepo repo;
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	/*@RequestMapping(method = RequestMethod.GET, value = "all")
	@ResponseBody
	public ResponseEntity<?> search(@RequestParam(value = "search", required=false) String search, @javax.validation.constraints.NotNull Pageable page) {
		PredicateBuilder<Accommodation> builder = new PredicateBuilder<>(Accommodation.class, "accommodation");
		if (search != null) {
			logger.info(search);
            Pattern pattern = Pattern.compile("([a-zA-Z\\.]+?)(:|<|>)([a-zA-Z1-9 ]+?),");
            java.util.regex.Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find()) {
            	logger.info("foudn pattern");
            	logger.info(matcher.group(1));
            	logger.info(matcher.group(2));
            	logger.info(matcher.group(3));
                builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
            }
        }
        BooleanExpression exp = builder.build();
        return new ResponseEntity<Page<AccommodationView>>(repo.findAll(exp, page).map(a -> mapper.map(a, AccommodationView.class)), HttpStatus.OK);
	}*/
	@RequestMapping(method = RequestMethod.GET, value = "all")
	@ResponseBody
	public ResponseEntity<?> searchDSL(@QuerydslPredicate(root=Accommodation.class) Predicate predicate , @javax.validation.constraints.NotNull Pageable page) {
        return new ResponseEntity<Page<AccommodationView>>(repo.findAll(predicate, page).map(a -> mapper.map(a, AccommodationView.class)), HttpStatus.OK);
	}
	
	
	@GetMapping(produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> getOne(@RequestParam(value="id", required=true) Long id) {
		Optional<Accommodation> accomodation = repo.findById(id);
		if(accomodation.isPresent()) {
			return new ResponseEntity<>(mapper.map(accomodation.get(), AccommodationView.class), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
