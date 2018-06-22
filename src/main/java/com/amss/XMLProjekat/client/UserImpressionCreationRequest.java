package com.amss.XMLProjekat.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @AllArgsConstructor @Setter @Getter
public class UserImpressionCreationRequest {
	private Long id;
	private Long accommodationId;
	private Integer rating;
	private String comment;
	private String registeredUserUsername;
}
