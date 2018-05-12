package com.amss.XMLProjekat.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class MessageView {
	private Long id;
	private String content;
	private String fromUserUsername;
	private String toUserUsername;
}
