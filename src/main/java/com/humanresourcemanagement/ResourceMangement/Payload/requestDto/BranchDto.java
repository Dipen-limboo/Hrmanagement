package com.humanresourcemanagement.ResourceMangement.Payload.requestDto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class BranchDto {
	@NotEmpty
	private String branch_name;

	@NotEmpty
	private String branch_address;
	
	@NotEmpty
	private String branch_phone;
	
	@NotNull
	private Long organization_id;
	private boolean is_out_of_valley = false;
	
	public BranchDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BranchDto(@NotEmpty String branch_name, @NotEmpty String branch_address, @NotEmpty String branch_phone,
			@NotNull Long organization_id, boolean is_out_of_valley) {
		super();
		this.branch_name = branch_name;
		this.branch_address = branch_address;
		this.branch_phone = branch_phone;
		this.organization_id = organization_id;
		this.is_out_of_valley = is_out_of_valley;
	}

	public String getBranch_name() {
		return branch_name;
	}

	public void setBranch_name(String branch_name) {
		this.branch_name = branch_name;
	}

	public String getBranch_address() {
		return branch_address;
	}

	public void setBranch_address(String branch_address) {
		this.branch_address = branch_address;
	}

	public String getBranch_phone() {
		return branch_phone;
	}

	public void setBranch_phone(String branch_phone) {
		this.branch_phone = branch_phone;
	}

	public boolean isIs_out_of_valley() {
		return is_out_of_valley;
	}

	public void setIs_out_of_valley(boolean is_out_of_valley) {
		this.is_out_of_valley = is_out_of_valley;
	}

	public Long getOrganization_id() {
		return organization_id;
	}

	public void setOrganization_id(Long organization_id) {
		this.organization_id = organization_id;
	}
	
		
}
