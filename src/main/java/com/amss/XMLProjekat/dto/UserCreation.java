package com.amss.XMLProjekat.dto;

import java.sql.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class UserCreation {
	@NotNull @Size(max=40, min = 2, message="Najmanje 2, najvise 40 karaktera.")
	private String firstName;
	@NotNull @Size(max=40, min = 2, message="Najmanje 2, najvise 40 karaktera.")
	private String lastName;
	@NotNull @Email(message = "Email should be valid.")
	private String email;
	@NotNull @Size(max=40, min = 3, message="Najmanje 3, najvise 40 karaktera.")
	private String username;
	@NotNull @Size(max=40, min = 3, message="Najmanje 3, najvise 40 karaktera.")
	private String password;
	private Date registrationDate;
}
