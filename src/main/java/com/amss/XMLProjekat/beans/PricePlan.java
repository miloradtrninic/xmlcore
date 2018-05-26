package com.amss.XMLProjekat.beans;
/***********************************************************************
 * Module:  PricePlan.java
 * Author:  komp
 * Purpose: Defines the Class PricePlan
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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
public class PricePlan {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@DateTimeFormat(pattern="dd-MM-yyyy")
	@Temporal(TemporalType.TIMESTAMP)
	private Date startingDate;
	@DateTimeFormat(pattern="dd-MM-yyyy")
	@Temporal(TemporalType.TIMESTAMP)
	private Date endingDate;
	private Double price;
	@ManyToOne(optional=false)
	private Accommodation accommodation;
}