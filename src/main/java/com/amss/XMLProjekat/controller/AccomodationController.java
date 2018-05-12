package com.amss.XMLProjekat.controller;

import java.util.List;
import java.util.regex.Pattern;

import org.hibernate.sql.ordering.antlr.OrderingSpecification;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.amss.XMLProjekat.beans.Accommodation;
import com.amss.XMLProjekat.dto.AccommodationView;
import com.amss.XMLProjekat.repository.AccomodationRepo;
import com.amss.XMLProjekat.repository.dsl.PredicateBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.sun.istack.internal.NotNull;

@RestController
@RequestMapping("/accommodation")
public class AccomodationController {

	@Autowired
	ModelMapper mapper;
	
	@Autowired
	AccomodationRepo repo;
	
	@RequestMapping(method = RequestMethod.GET, value = "all")
	@ResponseBody
	public ResponseEntity<?> search(@RequestParam(value = "search") String search, @NotNull Pageable page) {
		PredicateBuilder<Accommodation> builder = new PredicateBuilder<>(Accommodation.class, "accommodation");
		if (search != null) {
            Pattern pattern = Pattern.compile("(\b+?)(:|<|>)(\b+?),");
            java.util.regex.Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find()) {
                builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
            }
        }
        BooleanExpression exp = builder.build();
        return new ResponseEntity<Page<AccommodationView>>(repo.findAll(exp, page).map(a -> mapper.map(a, AccommodationView.class)), HttpStatus.OK);
	}
}
