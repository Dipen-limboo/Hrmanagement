package com.humanresourcemanagement.ResourceMangement.Payload.requestDto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class EmployeeUpdateDto {
	
	@NotNull
	private LocalDate joinDate;
	
	private LocalDate leaveDate;
	
	@NotNull
	private Long department;
	@NotNull
	private Long designation;

	public EmployeeUpdateDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EmployeeUpdateDto(@NotBlank LocalDate joinDate, LocalDate leaveDate, Long department, Long designation) {
		super();
		this.joinDate = joinDate;
		this.leaveDate = leaveDate;
		this.department = department;
		this.designation = designation;
	}

	public LocalDate getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(LocalDate joinDate) {
		this.joinDate = joinDate;
	}

	public LocalDate getLeaveDate() {
		return leaveDate;
	}

	public void setLeaveDate(LocalDate leaveDate) {
		this.leaveDate = leaveDate;
	}

	public Long getDepartment() {
		return department;
	}

	public void setDepartment(Long department) {
		this.department = department;
	}

	public Long getDesignation() {
		return designation;
	}

	public void setDesignation(Long designation) {
		this.designation = designation;
	}
	
	
}
