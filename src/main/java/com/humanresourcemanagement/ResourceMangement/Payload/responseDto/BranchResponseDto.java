package com.humanresourcemanagement.ResourceMangement.Payload.responseDto;

public class BranchResponseDto {
	private Long branch_id;
	private String branch_name;
	private String branch_address;
	private String branch_phone;
	private boolean is_out_of_valley;
	private Long organization_id;
	public BranchResponseDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public BranchResponseDto(String branch_name, String branch_address, String branch_phone, boolean is_out_of_valley) {
		super();
		this.branch_name = branch_name;
		this.branch_address = branch_address;
		this.branch_phone = branch_phone;
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

	public Long getBranch_id() {
		return branch_id;
	}

	public void setBranch_id(Long branch_id) {
		this.branch_id = branch_id;
	}

	public Long getOrganization_id() {
		return organization_id;
	}

	public void setOrganization_id(Long organization_id) {
		this.organization_id = organization_id;
	}
	
	
}
