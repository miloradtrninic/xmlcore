package com.amss.XMLProjekat.beans;
/***********************************************************************
 * Module:  Message.java
 * Author:  komp
 * Purpose: Defines the Class Message
 ***********************************************************************/

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
public class Message {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String content;
	
	@ManyToOne(optional=false)
	private User fromUser;
	
	@ManyToOne(optional=false)
	private User toUser;
	
	@ManyToOne(optional=false)
	private Reservation reservation;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateSent;
	

}