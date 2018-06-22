package com.amss.XMLProjekat.client;

import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @Setter @Getter
public class AccommodationViewResponse {
	private Long id;
	private Long externalKey;
	private Double rating;
	private Set<UserImpressionResponse> userImpressions;
}
