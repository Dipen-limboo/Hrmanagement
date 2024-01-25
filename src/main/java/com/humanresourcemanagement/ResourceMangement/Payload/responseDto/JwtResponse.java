package com.humanresourcemanagement.ResourceMangement.Payload.responseDto;

import java.time.LocalDate;
import java.util.List;

public class JwtResponse {
	private String token;
	private Long id;
	private String firstname;
	private String middlename;
	private String lastname;
	private LocalDate dateOfbirth;
	private String phone;
	private String username;
	private String email;
	private List<String> roles;
	private String status;
	private String gender;
	private String martial;

	
	
	public JwtResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	public JwtResponse(String token, Long id, String firstname, String middlename, String lastname, LocalDate dateOfbirth,
			String phone, String username, String email, List<String> roles, String status) {
		super();
		this.token = token;
		this.id = id;
		this.firstname = firstname;
		this.middlename = middlename;
		this.lastname = lastname;
		this.dateOfbirth = dateOfbirth;
		this.phone = phone;
		this.username = username;
		this.email = email;
		this.roles = roles;
		this.status = status;
	
	}


	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<String> getRoles() {
		return roles;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getMiddlename() {
		return middlename;
	}

	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public LocalDate getDateOfbirth() {
		return dateOfbirth;
	}

	public void setDateOfbirth(LocalDate dateOfbirth) {
		this.dateOfbirth = dateOfbirth;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public String getMartial() {
		return martial;
	}


	public void setMartial(String martial) {
		this.martial = martial;
	}
	
	
}
