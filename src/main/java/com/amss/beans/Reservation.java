package com.amss.beans;
/***********************************************************************
 * Module:  Reservation.java
 * Author:  komp
 * Purpose: Defines the Class Reservation
 ***********************************************************************/

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
public class Reservation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(optional=false)
	private Accommodation accommodation;
	@ManyToOne(optional=false)
	private RegisteredUser registeredUser;

	@Temporal(TemporalType.TIMESTAMP)
	private Date startingDate;
	@Temporal(TemporalType.TIMESTAMP)
	private Date endingDate;

	private boolean confirmed;
	
	@OneToMany(orphanRemoval=true)
	@Cascade(CascadeType.ALL)
	private List<Message> messages;

}