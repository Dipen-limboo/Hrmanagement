package com.humanresourcemanagement.ResourceMangement.Payload.requestDto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

public class PromotionDto {
	private LocalDate joinDate;
	
	@NotNull
	private Long subDepartment;
	
	@NotNull
	private Long designation;

	public PromotionDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PromotionDto(LocalDate joinDate, Long subDepartment, Long designation) {
		super();
		this.joinDate = joinDate;
		this.subDepartment = subDepartment;
		this.designation = designation;
	}

	public LocalDate getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(LocalDate joinDate) {
		this.joinDate = joinDate;
	}

	public Long getSubDepartment() {
		return subDepartment;
	}

	public void setSubDepartment(Long subDepartment) {
		this.subDepartment = subDepartment;
	}

	public Long getDesignation() {
		return designation;
	}

	public void setDesignation(Long designation) {
		this.designation = designation;
	}

}
