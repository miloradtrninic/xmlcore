package com.amss.XMLProjekat.client;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @Setter @Getter
@JsonIgnoreProperties(ignoreUnknown=true)
public class AccommodationRating {
	private Long id;
	private Long externalKey;
	private Double rating;
}
