package com.amss.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class UserImpressionCreation {
	private Integer rating;
	private String comment;
	private Long accommodationId;
}
