package com.amss.XMLProjekat.controller;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amss.XMLProjekat.beans.RegisteredUser;
import com.amss.XMLProjekat.beans.User;
import com.amss.XMLProjekat.dto.ChangePassword;
import com.amss.XMLProjekat.dto.RegisteredUserView;
import com.amss.XMLProjekat.dto.ResetPassword;
import com.amss.XMLProjekat.dto.UserCreation;
import com.amss.XMLProjekat.dto.UserView;
import com.amss.XMLProjekat.repository.RegisteredUserRepo;
import com.amss.XMLProjekat.repository.UserRepo;
import com.amss.XMLProjekat.security.EmailService;
import com.amss.XMLProjekat.security.JwtAuthenticationRequest;
import com.amss.XMLProjekat.security.JwtAuthenticationResponse;
import com.amss.XMLProjekat.security.JwtTokenUtil;
import com.google.common.net.UrlEscapers;

import lombok.extern.slf4j.Slf4j;

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
	private EmailService emailService;
	
	
	@Value("Authorization")
	private String tokenHeader;
	
	@Autowired
	ModelMapper mapper;
	
	@Value("mySecret")
	private String secret;
	@Value("${client.front.url.reset}")
	private String appURL;
	
	@GetMapping(value="/getone")
	public ResponseEntity<?> getByUsername(@RequestParam("username") String username) {
		Optional<User> user = userRepo.findOneByUsername(username);
		if(user.isPresent()) {
			if(user.get().getBlocked()) {
				return ResponseEntity.status(403).build();
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
					return ResponseEntity.status(403).build();
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
	@PostMapping(value="/changepass",
			produces=MediaType.APPLICATION_JSON_UTF8_VALUE,
			consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> insert(@RequestBody ChangePassword changePassword) {
		if(!SecurityContextHolder.getContext().getAuthentication().getName().equals(changePassword.getUsername())) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		Optional<User> found = userRepo.findOneByUsername(changePassword.getUsername());
		if(!found.isPresent()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if(!found.get().getUserType().equals("registered")) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		if(!passwordEncoder.matches(changePassword.getOldPassword(), found.get().getPassword())) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		found.get().setPassword(passwordEncoder.encode(changePassword.getNewPassword()));
		return new ResponseEntity<RegisteredUserView>(mapper.map(userRepo.save(found.get()), RegisteredUserView.class), HttpStatus.OK);
	}
	
	@GetMapping(value="/resetpassword", produces=MediaType.APPLICATION_JSON_UTF8_VALUE,
			consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Transactional
	public ResponseEntity<?> restPassword(@RequestParam String email) {
		Optional<User> found = userRepo.findOneByEmail(email);
		if(!found.isPresent()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		String hash = UrlEscapers.urlFragmentEscaper().escape(passwordEncoder.encode(found.get().getUsername()));
		found.get().setPassResetHash(hash);
		found.get().setBlocked(true);
		userRepo.save(found.get());
		emailService.sendSimpleMessage(found.get().getEmail(), "Password reset.", "Your password has been reset. Click this link to change your password:" + appURL + "?hash=" + found.get().getPassResetHash());
		return new ResponseEntity<RegisteredUserView>(mapper.map(found.get(), RegisteredUserView.class), HttpStatus.OK);
		
	}
	
	@PostMapping(value="/resetpassword", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Transactional
	public ResponseEntity<?> resetPassword(@RequestBody ResetPassword reset) {
		Optional<User> found = userRepo.findOneByPassResetHash(reset.getHash());
		if(!found.isPresent()) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		if(StringUtils.isEmpty(found.get().getPassResetHash())) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		if(!found.get().getUserType().equals("registered")) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		found.get().setPassword(passwordEncoder.encode(reset.getNewPassword()));
		found.get().setPassResetHash("");
		found.get().setBlocked(false);
		return new ResponseEntity<RegisteredUserView>(mapper.map(userRepo.save(found.get()), RegisteredUserView.class), HttpStatus.OK);
		
	}
}
