package com.amss.XMLProjekat.dto;

import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter @Setter @NoArgsConstructor
public class RegisteredUserView extends UserView {
	private Set<ReservationView> reservations;
	private Set<UserImpressionView> userImpression;
	private Boolean blocked;
}
