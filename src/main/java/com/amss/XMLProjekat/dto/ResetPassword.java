package com.amss.XMLProjekat.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @Setter @Getter
public class ResetPassword {
	@NotNull
	private String newPassword;
	@NotNull
	@Size(min=5, max=50)
	private String hash;
}
