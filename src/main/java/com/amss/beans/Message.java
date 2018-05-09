package com.amss.beans;
/***********************************************************************
 * Module:  Message.java
 * Author:  komp
 * Purpose: Defines the Class Message
 ***********************************************************************/

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
public class Message {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String content;
	
	@ManyToOne(optional=false)
	private User fromUser;
	
	@ManyToOne(optional=false)
	private User toUser;

}