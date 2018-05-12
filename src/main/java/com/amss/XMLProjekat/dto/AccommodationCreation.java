package com.amss.XMLProjekat.dto;

import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.amss.XMLProjekat.beans.AccommodationType;
import com.amss.XMLProjekat.beans.AdditionalService;
import com.amss.XMLProjekat.beans.Agent;
import com.amss.XMLProjekat.beans.Category;
import com.amss.XMLProjekat.beans.PricePlan;
import com.amss.XMLProjekat.beans.Restriction;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter @Setter @NoArgsConstructor 
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class AccommodationCreation {
	@XmlElement(required=false)
	private Long id;
	@XmlElement(required=true)
	private String location;
	@XmlElement(required=true)
	private AccommodationType type;
	@XmlElement(required=true)
	private String description;
	@XmlElement(required=true)
	private String name;
	//private Set<Document> images;
	@XmlElement(required=true)
	private Integer capacity;
	@XmlElement(required=true)
	private Set<AdditionalService> additionalServices;
	@XmlElement(required=true)
	private Set<PricePlan> pricePlans;
	@XmlElement(required=true)
	private Long agentId;
	@XmlElement(required=false)
	private Set<Restriction> restrictions;
	@XmlElement(required=true)
	private Category category;
}
