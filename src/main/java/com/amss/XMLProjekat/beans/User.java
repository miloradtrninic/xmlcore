package com.amss.XMLProjekat.beans;
/***********************************************************************
 * Module:  User.java
 * Author:  komp
 * Purpose: Defines the Class User
 ***********************************************************************/

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
public abstract class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String firstName;
	private String lastName;
	private String email;
	private String username;
	private String password;
	@Temporal(TemporalType.TIMESTAMP)
	private Date registrationDate;
	private Boolean blocked;
	@Transient
	private String userType;
	
	public String getUserType() {
		DiscriminatorValue val = this.getClass().getAnnotation( DiscriminatorValue.class );
	    return val == null ? null : val.value();
	}

}