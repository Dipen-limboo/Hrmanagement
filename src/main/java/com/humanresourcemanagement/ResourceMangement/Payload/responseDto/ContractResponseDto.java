package com.humanresourcemanagement.ResourceMangement.Payload.responseDto;

import java.time.LocalDate;

public class ContractResponseDto {
	private Long contract_id;
	
	private LocalDate join_date;
	
	private LocalDate end_date;
	
	private Long user_id;
	
	private Long designation_id;
	
	private Long section_id;
	
	private Long approver_id;

	public ContractResponseDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ContractResponseDto(Long contract_id, LocalDate join_date, LocalDate end_date, Long user_id,
			Long designation_id, Long section_id, Long approver_id) {
		super();
		this.contract_id = contract_id;
		this.join_date = join_date;
		this.end_date = end_date;
		this.user_id = user_id;
		this.designation_id = designation_id;
		this.section_id = section_id;
		this.approver_id = approver_id;
	}

	public Long getContract_id() {
		return contract_id;
	}

	public void setContract_id(Long contract_id) {
		this.contract_id = contract_id;
	}

	public LocalDate getJoin_date() {
		return join_date;
	}

	public void setJoin_date(LocalDate join_date) {
		this.join_date = join_date;
	}

	public LocalDate getEnd_date() {
		return end_date;
	}

	public void setEnd_date(LocalDate end_date) {
		this.end_date = end_date;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public Long getDesignation_id() {
		return designation_id;
	}

	public void setDesignation_id(Long designation_id) {
		this.designation_id = designation_id;
	}

	public Long getSection_id() {
		return section_id;
	}

	public void setSection_id(Long section_id) {
		this.section_id = section_id;
	}

	public Long getApprover_id() {
		return approver_id;
	}

	public void setApprover_id(Long approver_id) {
		this.approver_id = approver_id;
	}
	
	
}
