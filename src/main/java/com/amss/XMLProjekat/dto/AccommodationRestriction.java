package com.amss.XMLProjekat.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@XmlRootElement
@Getter @Setter @NoArgsConstructor
public class AccommodationRestriction {
	private Long accommodationId;
	private Date restrictionFrom;
	private Date restrictionTo;
}
