package com.amss.beans;

import java.util.List;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String location;
	@ManyToOne(optional=false, fetch=FetchType.EAGER)
	private AccommodationType type;

	private String description;

	private String name;
	@OneToMany(orphanRemoval=true, fetch=FetchType.EAGER)
	@Cascade(CascadeType.ALL)
	private List<Document> images;

	private Integer capacity;
	@ManyToMany(fetch=FetchType.EAGER)
	private List<AdditionalService> additionalServices;

	@OneToMany(orphanRemoval=true, fetch=FetchType.EAGER)
	@Cascade(CascadeType.ALL)
	private List<PricePlan> pricePlan;

	@ManyToOne(optional=false, fetch=FetchType.LAZY)
	private Agent agent;

	@OneToMany(orphanRemoval=true, fetch=FetchType.EAGER)
	@Cascade(CascadeType.ALL)
	private List<Restriction> restrictions;

	@ManyToMany(fetch=FetchType.EAGER)
	private Category category;

}