package com.humanresourcemanagement.ResourceMangement.Payload.requestDto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class EmployeeUpdateDto {
	
	
	private LocalDate joinDate;
	
	private LocalDate endDate;
	
	
	private Long subDepartment;
	
	private Long designation;

	public EmployeeUpdateDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EmployeeUpdateDto(LocalDate joinDate, LocalDate leaveDate, Long subdepartment, Long designation) {
		super();
		this.joinDate = joinDate;
		this.endDate = leaveDate;
		this.subDepartment = subdepartment;
		this.designation = designation;
	}


	public LocalDate getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(LocalDate joinDate) {
		this.joinDate = joinDate;
	}

	public LocalDate getLeaveDate() {
		return endDate;
	}

	public void setLeaveDate(LocalDate leaveDate) {
		this.endDate = leaveDate;
	}

	public Long getSubDepartment() {
		return subDepartment;
	}

	public void setSubDepartment(Long subdepartment) {
		this.subDepartment = subdepartment;
	}

	public Long getDesignation() {
		return designation;
	}

	public void setDesignation(Long designation) {
		this.designation = designation;
	}
	
	
}
