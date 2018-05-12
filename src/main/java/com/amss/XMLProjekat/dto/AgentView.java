package com.amss.XMLProjekat.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter @Setter @NoArgsConstructor 
public class AgentView extends UserView {
	private Boolean verified;
	private String pib;
}
