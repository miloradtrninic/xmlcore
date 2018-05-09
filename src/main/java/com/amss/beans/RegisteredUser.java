package com.amss.beans;
/***********************************************************************
 * Module:  RegisteredUser.java
 * Author:  komp
 * Purpose: Defines the Class RegisteredUser
 ***********************************************************************/

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("registered")
@Getter @Setter @NoArgsConstructor
public class RegisteredUser extends User {
	@OneToMany(orphanRemoval=true, mappedBy="registeredUser")
	@Cascade(CascadeType.ALL)
	private List<Reservation> reservations;
	
	@OneToMany(orphanRemoval=true, mappedBy="registeredUser")
	@Cascade(CascadeType.ALL)
	private List<UserImpression> userImpression;
	
	private Boolean blocked;

}