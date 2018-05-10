package com.amss.beans;

import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
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
   @OneToMany(mappedBy="agent", orphanRemoval=true)
   @Cascade(CascadeType.ALL)
   private Set<Accommodation> accommodations;
   
   private Boolean verified;
   
   private String pib;

}