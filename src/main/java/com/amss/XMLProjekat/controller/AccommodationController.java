package com.amss.XMLProjekat.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.assertj.core.util.Arrays;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.data.querydsl.QSort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.amss.XMLProjekat.XmlProjekatApplication;
import com.amss.XMLProjekat.beans.Accommodation;
import com.amss.XMLProjekat.client.AccommodationRating;
import com.amss.XMLProjekat.client.AccommodationViewResponse;
import com.amss.XMLProjekat.dto.AccommodationView;
import com.amss.XMLProjekat.repository.AccomodationRepo;
import com.amss.XMLProjekat.repository.dsl.PredicateBuilder;
import com.amss.XMLProjekat.repository.dsl.QDSLPageable;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/accommodation")
@Slf4j
public class AccommodationController {

	@Autowired
	ModelMapper mapper;
	
	@Autowired
	AccomodationRepo repo;
	
	@Value("${rating-service.url}")
	public String ratingUrl;
	
	
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
	}
	*/
	/*@RequestMapping(method = RequestMethod.GET, value = "all")
	@ResponseBody
	@Secured({"ROLE_ANONYMOUS", "ROLE_agent", "ROLE_admin", "ROLE_registered"})
	public ResponseEntity<?> searchDSL(@QuerydslPredicate(root=Accommodation.class) Predicate predicate,  Pageable page) {
		AtomicBoolean hasRating = new AtomicBoolean(false);
		AtomicBoolean ascending = new AtomicBoolean(false);
		List<Order> orders = page.getSort().stream().filter(o -> {
			if(o.getProperty().equals("rating")) {
				hasRating.set(true);
				ascending.set(o.getDirection().equals(Direction.ASC));
				return false;
			}
			return true;
		}).collect(Collectors.toList());
		Pageable p = new Pageable() {
			
			@Override
			public Pageable previousOrFirst() {
				// TODO Auto-generated method stub
				return page.previousOrFirst();
			}
			
			@Override
			public Pageable next() {
				// TODO Auto-generated method stub
				return page.next();
			}
			
			@Override
			public boolean hasPrevious() {
				// TODO Auto-generated method stub
				return page.hasPrevious();
			}
			
			@Override
			public Sort getSort() {
				// TODO Auto-generated method stub
				return new Sort(orders);
			}
			
			@Override
			public int getPageSize() {
				// TODO Auto-generated method stub
				return page.getPageSize();
			}
			
			@Override
			public int getPageNumber() {
				// TODO Auto-generated method stub
				return page.getPageNumber();
			}
			
			@Override
			public long getOffset() {
				// TODO Auto-generated method stub
				return page.getOffset();
			}
			
			@Override
			public Pageable first() {
				// TODO Auto-generated method stub
				return page.first();
			}
		};
		Page<AccommodationView> result = repo.findAll(predicate, p).map(a -> mapper.map(a, AccommodationView.class));
		if(hasRating.get()) {
			RestTemplate request = new RestTemplate();
			ResponseEntity<Object[]> response = request.postForEntity(ratingUrl+ "accommodation/all/in", result.getContent()
								.stream()
								.map(a -> a.getId())
								.collect(Collectors.toList()), Object[].class);
			if(!response.getStatusCode().equals(HttpStatus.OK)) {
				return response;
			}
			List<AccommodationRating> ratings = new ArrayList<>();
			for(int i = 0; i < response.getBody().length; i++) {
				ratings.add((AccommodationRating)response.getBody()[i]);
			}
			result.getContent().forEach(r -> r.setRating(0.0));
			for(AccommodationView a:result.getContent()) {
				Optional<AccommodationRating> rating = ratings.stream().filter(r -> r.getExternalKey() == a.getId()).findFirst();
				if(rating.isPresent()) {
					a.setRating(rating.get().getRating());
				}
			}
			result.getSort().and(new Sort("rating"));
			/*result.getContent().sort((r1, r2) -> {
				if(ascending.get())
					return Double.compare(r1.getRating(), 5.0);
				else
					return Double.compare(r2.getRating(), 5.0);
			});
		}
        return new ResponseEntity<Page<AccommodationView>>(result, HttpStatus.OK);
	}*/
	@RequestMapping(method = RequestMethod.GET, value = "all")
	@ResponseBody
	@Secured({"ROLE_ANONYMOUS", "ROLE_agent", "ROLE_admin", "ROLE_registered"})
	public ResponseEntity<?> searchDSL(@QuerydslPredicate(root=Accommodation.class) Predicate predicate,  Pageable page) {
		Page<AccommodationView> result = repo.findAll(predicate, page).map(a -> mapper.map(a, AccommodationView.class));
		return new ResponseEntity<Page<AccommodationView>>(result, HttpStatus.OK);
	}
	
	@GetMapping(produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Secured({"ROLE_ANONYMOUS", "ROLE_agent", "ROLE_admin", "ROLE_registered"})
	public ResponseEntity<?> getOne(@RequestParam(value="id", required=true) Long id) {
		Optional<Accommodation> accomodation = repo.findById(id);
		if(accomodation.isPresent()) {
			log.info(ratingUrl + "accommodation?id=" + id);
			RestTemplate client = new RestTemplate();
			ResponseEntity<AccommodationViewResponse> response = client.getForEntity(ratingUrl + "accommodation?id=" + id, AccommodationViewResponse.class);
			if(!response.getStatusCode().equals(HttpStatus.OK)) {
				return response;
			}
			accomodation.get().setRating(response.getBody().getRating());
			return new ResponseEntity<>(mapper.map(accomodation.get(), AccommodationView.class), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

}
