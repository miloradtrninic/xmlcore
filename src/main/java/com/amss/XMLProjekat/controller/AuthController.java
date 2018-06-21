package com.amss.XMLProjekat.controller;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.HibernateException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.amss.XMLProjekat.beans.RegisteredUser;
import com.amss.XMLProjekat.beans.User;
import com.amss.XMLProjekat.dto.RegisteredUserView;
import com.amss.XMLProjekat.dto.UserCreation;
import com.amss.XMLProjekat.dto.UserView;
import com.amss.XMLProjekat.repository.RegisteredUserRepo;
import com.amss.XMLProjekat.repository.UserRepo;
import com.amss.XMLProjekat.security.JwtAuthenticationRequest;
import com.amss.XMLProjekat.security.JwtAuthenticationResponse;
import com.amss.XMLProjekat.security.JwtTokenUtil;
import com.amss.XMLProjekat.security.UserDetailsCustom;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@RestController
@RequestMapping(value="/auth")
public class AuthController {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private RegisteredUserRepo registeredUserRepo;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService myAppUserDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	private MessageSource messages;
	
	@Value("Authorization")
	private String tokenHeader;
	
	@Autowired
	ModelMapper mapper;
	
	@Value("mySecret")
	private String secret;
	
	@GetMapping(value="/getone")
	public ResponseEntity<?> getByUsername(@RequestParam("username") String username) {
		Optional<User> user = userRepo.findOneByUsername(username);
		if(user.isPresent()) {
			if(user.get().getBlocked()) {
				return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(messages.getMessage("auth.msg.accLocked", null, new Locale("en)")));
			}
			return ResponseEntity.ok(mapper.map(user.get(), UserView.class));
		} else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User not found");
		} 
	}
	
	@RequestMapping(value="/login",
			method=RequestMethod.POST,
			consumes= {"application/json", "application/json;charset=UTF-8"},
			produces= {"application/json"})
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest) throws AuthenticationException {
		try{
			Optional<User> userName = userRepo.findOneByUsername(authenticationRequest.getUsername());
	
			
			if(userName.isPresent()) {
				if(!userName.get().getBlocked()) {
					final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
							authenticationRequest.getUsername(),
							authenticationRequest.getPassword()));
					SecurityContextHolder.getContext().setAuthentication(authentication);
					final UserDetails userDetails = myAppUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
					final String token = jwtTokenUtil.generateToken(userDetails);
					return ResponseEntity.ok(new JwtAuthenticationResponse(token,userDetails.getUsername(), 
							userDetails.getAuthorities()
							.stream()
							.map(r -> r.getAuthority())
							.collect(Collectors.toList())));
				} else {
					return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(messages.getMessage("auth.msg.accLocked", null, new Locale("en)")));
				}
			} else {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User not found");
			}
		} catch (BadCredentialsException | UsernameNotFoundException e){
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}
	}
	
	@PostMapping(value="/register",
			produces=MediaType.APPLICATION_JSON_UTF8_VALUE,
			consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> insert(@RequestBody UserCreation newEntity) {
		Optional<User> found = userRepo.findOneByUsername(newEntity.getUsername());
		if(found.isPresent()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		RegisteredUser newUser = mapper.map(newEntity, RegisteredUser.class);
		newUser.setBlocked(false);
		newUser.setReservations(new HashSet<>());
		newUser.setUserImpression(new HashSet<>());
		newUser.setPassword(passwordEncoder.encode(newEntity.getPassword()));
		newUser.setRegistrationDate(new Date());
		return new ResponseEntity<RegisteredUserView>(mapper.map(registeredUserRepo.save(newUser), RegisteredUserView.class), HttpStatus.OK);
	}
}
