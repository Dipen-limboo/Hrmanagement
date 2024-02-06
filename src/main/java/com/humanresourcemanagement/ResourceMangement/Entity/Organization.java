package com.humanresourcemanagement.ResourceMangement.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;

@Entity
@Table(name="Tbl_organization")
public class Organization {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="org_id")
	private Long id;
	
	@Column(name="org_name")
	private String orgName;
	
	@Column(name="org_address")
	private String orgAddress;
		
	@Column(name="org_email")
	@Email
	private String orgEmail;
	
	@Column(name="org_phone")
	private String orgPhone;
	
	@Column(name="org_website")
	private String orgWebsite;
	
	@Column(name="org_logo_path")
	private String path; 

	public Organization() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Organization(Long id, String orgName, String orgAddress, @Email String orgEmail, String orgPhone,
			String orgWebsite) {
		super();
		this.id = id;
		this.orgName = orgName;
		this.orgAddress = orgAddress;
		this.orgEmail = orgEmail;
		this.orgPhone = orgPhone;
		this.orgWebsite = orgWebsite;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgAddress() {
		return orgAddress;
	}

	public void setOrgAddress(String orgAddress) {
		this.orgAddress = orgAddress;
	}

	public String getOrgEmail() {
		return orgEmail;
	}

	public void setOrgEmail(String orgEmail) {
		this.orgEmail = orgEmail;
	}

	public String getOrgPhone() {
		return orgPhone;
	}

	public void setOrgPhone(String orgPhone) {
		this.orgPhone = orgPhone;
	}

	public String getOrgWebsite() {
		return orgWebsite;
	}

	public void setOrgWebsite(String orgWebsite) {
		this.orgWebsite = orgWebsite;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
