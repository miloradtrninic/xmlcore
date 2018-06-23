package com.amss.XMLProjekat.beans;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Getter @Setter @NoArgsConstructor
@Accessors
public class Accommodation {
	@Id
	private Long id;

	private String location;
	@ManyToOne(optional=false, fetch=FetchType.EAGER)
	private AccommodationType type;

	private String description;

	private String name;
	@OneToMany(orphanRemoval=true, fetch=FetchType.EAGER)
	@Cascade(CascadeType.ALL)
	private Set<Document> images;

	private Integer capacity;
	@ManyToMany(fetch=FetchType.EAGER)
	private Set<AdditionalService> additionalServices;

	@OneToMany(orphanRemoval=true, fetch=FetchType.EAGER, mappedBy="accommodation")
	@Cascade(CascadeType.ALL)
	private Set<PricePlan> pricePlan;

	@ManyToOne(optional=false)
	private Agent agent;

	@OneToMany(orphanRemoval=true, mappedBy="accommodation", fetch=FetchType.EAGER)
	@Cascade(CascadeType.ALL)
	private Set<Restriction> restrictions;
	
	@OneToMany(orphanRemoval=true, mappedBy="accommodation", fetch=FetchType.EAGER)
	@Cascade(CascadeType.ALL)
	private Set<Reservation> reservations;

	@ManyToOne(fetch=FetchType.EAGER, optional=false)
	private Category category;
	
	@OneToMany(mappedBy="accommodation", fetch=FetchType.EAGER)
	private Set<UserImpression> userImpressions;
	
	private double rating;

}