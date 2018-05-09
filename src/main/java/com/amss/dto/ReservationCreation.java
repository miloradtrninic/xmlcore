package com.amss.dto;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class ReservationCreation {
	private Long accommodationId;
	private Date startingDate;
	private Date endingDate;
	
}
