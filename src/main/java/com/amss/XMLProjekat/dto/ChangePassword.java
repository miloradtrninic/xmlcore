package com.amss.XMLProjekat.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class ChangePassword {
	@NotNull
	private String username;
	@NotNull
    @Size(min=5, max=50)
	private String oldPassword;
	@NotNull
    @Size(min=5, max=50)
	private String newPassword;
}
