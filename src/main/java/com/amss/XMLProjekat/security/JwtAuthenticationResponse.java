package com.amss.XMLProjekat.security;

import java.io.Serializable;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class JwtAuthenticationResponse implements Serializable {

    private static final long serialVersionUID = 1250166508152483573L;

    private final String token;
    
    private String username;
	private List<String> roles;

	
    public JwtAuthenticationResponse(String token, String username, List<String> roles) {
		super();
		this.token = token;
		this.username = username;
		this.roles = roles;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public String getToken() {
        return this.token;
    }

    
}
