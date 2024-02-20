package com.humanresourcemanagement.ResourceMangement.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="Tbl_branch")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Branch {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="branch_id")
	private Long id;
	
	@Column(name="branch_name")
	private String branchName;
	
	@Column(name="branch_address")
	private String branchAddress;
	
	@Column(name="branch_telephone")
	private String branchPhone;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="organization_id")
	private Organization organization;
	
	@Column(name="isOutBranch")
	private boolean isOutOfValley = false;

	public Branch() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Branch(Long id, String branchName, String branchAddress, String branchPhone, boolean isOutOfValley) {
		super();
		this.id = id;
		this.branchName = branchName;
		this.branchAddress = branchAddress;
		this.branchPhone = branchPhone;
		this.isOutOfValley = isOutOfValley;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getBranchAddress() {
		return branchAddress;
	}

	public void setBranchAddress(String branchAddress) {
		this.branchAddress = branchAddress;
	}

	public String getBranchPhone() {
		return branchPhone;
	}

	public void setBranchPhone(String branchPhone) {
		this.branchPhone = branchPhone;
	}

	public boolean isOutOfValley() {
		return isOutOfValley;
	}

	public void setOutOfValley(boolean isOutOfValley) {
		this.isOutOfValley = isOutOfValley;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	} 
	
	
}
