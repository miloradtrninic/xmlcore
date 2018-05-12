package com.amss.XMLProjekat.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
@XmlRootElement(name="AccommodationReserve")
public class AccommodationReserve {
	@XmlElement(required=true)
	private Long reservationId;
	@XmlElement(required=true)
	private Boolean confirmed;
}
