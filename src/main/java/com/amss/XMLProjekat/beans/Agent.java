package com.amss.XMLProjekat.beans;

import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("agent")
@Getter @Setter @NoArgsConstructor
public class Agent extends User {
	@OneToMany(mappedBy="agent", orphanRemoval=true, fetch=FetchType.LAZY)
	@Cascade(CascadeType.ALL)
	private Set<Accommodation> accommodations;
	private String pib;

	public void setAccommodations(Set<Accommodation> roles) {
		if (this.accommodations == null) {
			this.accommodations = roles;
		} else {
			this.accommodations.retainAll(roles);
			this.accommodations.addAll(roles);
		}
	}
}