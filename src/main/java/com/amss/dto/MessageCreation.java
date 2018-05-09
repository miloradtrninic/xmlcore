package com.amss.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class MessageCreation {
	private String content;
	private Long toUserId;
	private Long reservationId;
}
