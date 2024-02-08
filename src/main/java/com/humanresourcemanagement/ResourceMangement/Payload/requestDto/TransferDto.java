package com.humanresourcemanagement.ResourceMangement.Payload.requestDto;

import jakarta.validation.constraints.NotNull;

public class TransferDto {
	@NotNull
	private Long section_id;
	
	@NotNull
	private Long designation_id;
	
	@NotNull
	private Long branch_id;

	public TransferDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TransferDto(@NotNull Long section_id, @NotNull Long designation_id, @NotNull Long branch_id) {
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
	
	
}
