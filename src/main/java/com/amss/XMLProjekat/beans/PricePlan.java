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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
public class PricePlan {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Temporal(TemporalType.TIMESTAMP)
	private Date startingDate;
	@Temporal(TemporalType.TIMESTAMP)
	private Date endingDate;
	private Double price;

}