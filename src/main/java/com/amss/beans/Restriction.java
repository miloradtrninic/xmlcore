package com.amss.beans;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
public class Restriction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Temporal(TemporalType.TIMESTAMP)
	private Date restrictionFrom;
	@Temporal(TemporalType.TIMESTAMP)
	private Date restrictionTo;

}