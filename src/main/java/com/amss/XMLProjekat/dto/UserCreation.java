package com.amss.XMLProjekat.dto;

import java.sql.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class UserCreation {
	private String firstName;
	private String lastName;
	private String email;
	private String username;
	private String password;
	private Date registrationDate;
}
