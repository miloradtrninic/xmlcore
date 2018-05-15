package com.amss.XMLProjekat.beans;
/***********************************************************************
 * Module:  Restriction.java
 * Author:  komp
 * Purpose: Defines the Class Restriction
 ***********************************************************************/

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
public class Restriction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@DateTimeFormat(pattern="dd-MM-yyyy")
	@Temporal(TemporalType.TIMESTAMP)
	private Date restrictionFrom;
	@DateTimeFormat(pattern="dd-MM-yyyy")
	@Temporal(TemporalType.TIMESTAMP)
	private Date restrictionTo;
	@ManyToOne(optional=false)
	private Accommodation accommodation;
	
}