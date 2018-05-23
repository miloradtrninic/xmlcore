package com.amss.XMLProjekat.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class MessageCreation {
	private String content;
	private String toUserUsername;
	private Long reservationId;
}
