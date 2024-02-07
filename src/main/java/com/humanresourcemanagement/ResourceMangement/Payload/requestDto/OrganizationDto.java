package com.humanresourcemanagement.ResourceMangement.Payload.requestDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class OrganizationDto {
	@NotEmpty
	private String org_name;
	
	@NotEmpty
	private String org_address;
	
	@NotEmpty
	@Email
	private String org_email;
	
	private String org_phone;
	
	private String org_website;
	
	private String org_logo_path;

	public OrganizationDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrganizationDto(@NotEmpty String org_name, @NotEmpty String org_address, @NotEmpty @Email String org_email,
			String org_phone, String org_website, String org_logo_path) {
		super();
		this.org_name = org_name;
		this.org_address = org_address;
		this.org_email = org_email;
		this.org_phone = org_phone;
		this.org_website = org_website;
		this.org_logo_path = org_logo_path;
	}

	public String getOrg_name() {
		return org_name;
	}

	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}

	public String getOrg_address() {
		return org_address;
	}

	public void setOrg_address(String org_address) {
		this.org_address = org_address;
	}

	public String getOrg_email() {
		return org_email;
	}

	public void setOrg_email(String org_email) {
		this.org_email = org_email;
	}

	public String getOrg_phone() {
		return org_phone;
	}

	public void setOrg_phone(String org_phone) {
		this.org_phone = org_phone;
	}

	public String getOrg_website() {
		return org_website;
	}

	public void setOrg_website(String org_website) {
		this.org_website = org_website;
	}

	public String getOrg_logo_path() {
		return org_logo_path;
	}

	public void setOrg_logo_path(String org_logo_path) {
		this.org_logo_path = org_logo_path;
	}

}
