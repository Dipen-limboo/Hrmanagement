package com.humanresourcemanagement.ResourceMangement.Payload.requestDto;

import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotEmpty;

public class FamilyInfoDto {
	@NotEmpty
	private String firstname;
	
	private String middlename;
	
	@NotEmpty
	private String lastname;
	
	@NotEmpty
	private Set<String> relation;
	
	private String phone;
	
	private MultipartFile file;

	public FamilyInfoDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FamilyInfoDto(@NotEmpty String firstname, String middlename, @NotEmpty String lastname,
			@NotEmpty Set<String> relation, String phone) {
		super();
		this.firstname = firstname;
		this.middlename = middlename;
		this.lastname = lastname;
		this.relation = relation;
		this.phone = phone;
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

	public Set<String> getRelation() {
		return relation;
	}

	public void setRelation(Set<String> relation) {
		this.relation = relation;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
	
}
