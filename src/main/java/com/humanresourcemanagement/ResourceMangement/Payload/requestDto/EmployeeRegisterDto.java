package com.humanresourcemanagement.ResourceMangement.Payload.requestDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class EmployeeRegisterDto {
	@NotEmpty
	private String firstName;
	
	private String middleName;
	
	@NotEmpty
	private String lastName;
	
	@NotEmpty
	@Email
	private String email;
	
	private String password;

	public EmployeeRegisterDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EmployeeRegisterDto(@NotEmpty String firstName, String middleName, @NotEmpty String lastName,
			@NotEmpty @Email String email, String password) {
		super();
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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
	
	
}
