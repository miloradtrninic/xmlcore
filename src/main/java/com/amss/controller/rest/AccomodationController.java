package com.amss.controller.rest;

import java.util.List;
import java.util.regex.Pattern;

import org.hibernate.sql.ordering.antlr.OrderingSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.amss.beans.Accommodation;
import com.amss.repository.AccomodationRepo;
import com.amss.repository.dsl.PredicateBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;

@RestController
@RequestMapping("/accomodation")
public class AccomodationController {

	@Autowired
	AccomodationRepo repo;
	
	//TODO ORDER & PAGING
	@RequestMapping(method = RequestMethod.GET, value = "/getAll")
	@ResponseBody
	public ResponseEntity<?> search(@RequestParam(value = "search") String search) {
		PredicateBuilder<Accommodation> builder = new PredicateBuilder<>(Accommodation.class, "accommodation");
		if (search != null) {
            Pattern pattern = Pattern.compile("(\b+?)(:|<|>)(\b+?),");
            java.util.regex.Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find()) {
                builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
            }
        }
        BooleanExpression exp = builder.build();
        return new ResponseEntity<Iterable<Accommodation>>(repo.findAll(exp), HttpStatus.OK);
	}
}
