package com.humanresourcemanagement.ResourceMangement.Payload.responseDto;

import java.util.Set;

public class UserInfoDto {
	private Long id;
	private String firstName;
	private String middleName;
	private String LastName;
	private String email;
	private String Role;
	private String phone;
	public UserInfoDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserInfoDto(Long id, String firstName, String middleName, String lastName, String email, String role,
			String phone) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.middleName = middleName;
		LastName = lastName;
		this.email = email;
		Role = role;
		this.phone = phone;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
		return LastName;
	}
	public void setLastName(String lastName) {
		LastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRole() {
		return Role;
	}
	public void setRole(String role) {
		Role = role;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
}
