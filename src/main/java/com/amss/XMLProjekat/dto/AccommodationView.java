package com.amss.XMLProjekat.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class AccommodationView {
	private Long id;
	private String location;
	private String typeName;
	private String description;
	private String name;
	private List<String> imagesPath;
	private Integer capacity;
	private List<String> additionalServicesName;
	private List<PricePlanView> pricePlan;
	private String agentUsername;
	private List<RestrictionView> restrictions;
	private String categoryName;
}
