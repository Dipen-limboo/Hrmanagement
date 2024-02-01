package com.humanresourcemanagement.ResourceMangement.Payload.responseDto;

import java.time.LocalDate;

public class PromotionResponseDto {
	private Long id;
	
	private LocalDate join_date;
	
	private LocalDate end_date;
	
	private String designation;
	
	private String sub_department;
	
	private String approved_by;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getSub_department() {
		return sub_department;
	}

	public void setSub_department(String sub_department) {
		this.sub_department = sub_department;
	}

	public String getApproved_by() {
		return approved_by;
	}

	public void setApproved_by(String approved_by) {
		this.approved_by = approved_by;
	}

	public PromotionResponseDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PromotionResponseDto(Long id, LocalDate join_date, LocalDate end_date, String designation,
			String sub_department, String approved_by) {
		super();
		this.id = id;
		this.join_date = join_date;
		this.end_date = end_date;
		this.designation = designation;
		this.sub_department = sub_department;
		this.approved_by = approved_by;
	}
	
	
}
