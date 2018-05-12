package com.amss.XMLProjekat.beans;
/***********************************************************************
 * Module:  UserImpression.java
 * Author:  komp
 * Purpose: Defines the Class UserImpression
 ***********************************************************************/

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
public class UserImpression {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Integer rating;

	private String comment;
	
	@ManyToOne(optional=false)
	private Accommodation accommodation;
	
	@ManyToOne(optional=false)
	private RegisteredUser registeredUser;

	private boolean verified;

}