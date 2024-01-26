package com.humanresourcemanagement.ResourceMangement.Payload.responseDto;

import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotEmpty;

public class FamilyDto {
	@NotEmpty
	private String firstname;
	
	private String middlename;
	
	@NotEmpty
	private String lastname;
	
	@NotEmpty
	private Set<String> relation;
	
	private String phone;
	
	private String file;
	
	private Long userId;
	public FamilyDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FamilyDto(@NotEmpty String firstname, String middlename, @NotEmpty String lastname,
			@NotEmpty Set<String> relation, String phone, String file) {
		super();
		this.firstname = firstname;
		this.middlename = middlename;
		this.lastname = lastname;
		this.relation = relation;
		this.phone = phone;
		this.file = file;
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

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	
}
