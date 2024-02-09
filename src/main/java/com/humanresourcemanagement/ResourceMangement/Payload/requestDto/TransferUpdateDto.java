package com.humanresourcemanagement.ResourceMangement.Payload.requestDto;

import java.time.LocalDate;

public class TransferUpdateDto {

	private Long section_id;
	

	private Long designation_id;

	private Long branch_id;
	
	private String remarks;

	private LocalDate transferDate;

	public TransferUpdateDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TransferUpdateDto( Long section_id,  Long designation_id,  Long branch_id) {
		super();
		this.section_id = section_id;
		this.designation_id = designation_id;
		this.branch_id = branch_id;
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

	public Long getBranch_id() {
		return branch_id;
	}

	public void setBranch_id(Long branch_id) {
		this.branch_id = branch_id;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public LocalDate getTransferDate() {
		return transferDate;
	}

	public void setTransferDate(LocalDate transferDate) {
		this.transferDate = transferDate;
	}
}
