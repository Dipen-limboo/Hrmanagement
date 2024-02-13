package com.humanresourcemanagement.ResourceMangement.Payload.requestDto;

import java.time.LocalDate;

public class ContractUpdateDto {
	private LocalDate join_date;
	
	private LocalDate end_date;
	
	private Long section_id;
	
	private Long designation_id;

	public ContractUpdateDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ContractUpdateDto(LocalDate join_date, LocalDate end_date, Long section_id, Long designation_id) {
		super();
		this.join_date = join_date;
		this.end_date = end_date;
		this.section_id = section_id;
		this.designation_id = designation_id;
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
	
	
}
