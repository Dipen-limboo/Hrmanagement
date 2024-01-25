package com.humanresourcemanagement.ResourceMangement.Payload.requestDto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public class ChangedPasswordDto {
	@NotEmpty
	private String email;
	
	@NotEmpty
	@Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$")
	private String password;
	
	@NotEmpty
	private String confirmPassword;
	
	public ChangedPasswordDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ChangedPasswordDto(@NotEmpty String email, @NotEmpty String password, String confirmPassword) {
		super();
		this.email = email;
		this.password = password;
		this.confirmPassword = confirmPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

}
