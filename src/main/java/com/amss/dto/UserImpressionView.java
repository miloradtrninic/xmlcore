package com.amss.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class UserImpressionView {
	private Long id;
	private Integer rating;
	private String comment;
	private String accommodationName;
	private String registeredUserUsername;
	private boolean verified;
}
