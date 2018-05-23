package com.amss.XMLProjekat.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class MessageView {
	private Long id;
	private String content;
	private String fromUserUsername;
	private String toUserUsername;
	@JsonFormat(pattern="dd-MM-yyyy hh:mm:ss")
	private Date dateSent;
}
