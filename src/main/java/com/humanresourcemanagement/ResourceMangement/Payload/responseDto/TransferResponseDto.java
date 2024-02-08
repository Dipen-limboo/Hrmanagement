package com.humanresourcemanagement.ResourceMangement.Payload.responseDto;

import java.time.LocalDate;

public class TransferResponseDto {
	private Long transfer_id;
	private Long section_id;
	private Long designation_id;
	private Long user_id;
	private Long branch_id;
	private LocalDate transfer_date;
	private String remarks;
	public TransferResponseDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TransferResponseDto(Long transfer_id, Long section_id, Long designation_id, Long user_id, Long branch_id) {
		super();
		this.transfer_id = transfer_id;
		this.section_id = section_id;
		this.designation_id = designation_id;
		this.user_id = user_id;
		this.branch_id = branch_id;
	}

	public Long getTransfer_id() {
		return transfer_id;
	}

	public void setTransfer_id(Long transfer_id) {
		this.transfer_id = transfer_id;
	}

	public Long getSection_id() {
		return section_id;
	}

	public void setSection_id(Long section_id) {
		this.section_id = section_id;
	}

	public Long getDesignation_id() {
		return designation_id;
	}

	public void setDesignation_id(Long designation_id) {
		this.designation_id = designation_id;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public Long getBranch_id() {
		return branch_id;
	}

	public void setBranch_id(Long branch_id) {
		this.branch_id = branch_id;
	}

	public LocalDate getTransfer_date() {
		return transfer_date;
	}

	public void setTransfer_date(LocalDate transfer_date) {
		this.transfer_date = transfer_date;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
		
}
