package com.amss.XMLProjekat.client;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @Setter @Getter
public class UserImpressionResponse {
	private Long id;
	private Long accommodationExternalKey;
	private Integer rating;
	private String comment;
	private String registeredUserUsername;
	private Double averageRating;
}
