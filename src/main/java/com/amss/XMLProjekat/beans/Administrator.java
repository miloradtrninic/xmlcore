package com.amss.XMLProjekat.beans;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("admin")
@Getter @Setter @NoArgsConstructor
public class Administrator extends User {
   
 

}