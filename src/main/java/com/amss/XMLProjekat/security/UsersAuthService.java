package com.amss.XMLProjekat.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.amss.XMLProjekat.beans.User;
import com.amss.XMLProjekat.repository.UserRepo;

import lombok.extern.slf4j.Slf4j;



@Service("myAppUserDetailsService")
public class UsersAuthService implements UserDetailsService {

	@Autowired
	private UserRepo userDao;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userDao.findOneByUsername(username);
		if(!user.isPresent())
			throw new UsernameNotFoundException("Username not found.");
		
		List<SimpleGrantedAuthority> roleAuths = new ArrayList<>();
		roleAuths.add(new SimpleGrantedAuthority("ROLE_" + user.get().getUserType()));
		UserDetails userDetails = new UserDetailsCustom(user.get().getUsername(), user.get().getPassword(), roleAuths);
		return userDetails;
	}

}
