package com.humanresourcemanagement.ResourceMangement.Payload.requestDto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class EmployeeUpdateDto {
	
	@NotEmpty
	private String employeeId;
	
	private LocalDate joinDate;
	
	private LocalDate endDate;
	
	@NotNull
	private Long subDepartment;
	
	@NotNull
	private Long designation;
	
	private String remarks;
	
	@NotNull
	private Long workingType;
	
	@NotNull
	private Long jobType;
	
	@NotNull
	private Long branch;
	
	@NotNull
	private Long grade;

	public EmployeeUpdateDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public EmployeeUpdateDto(@NotEmpty String employeeId, LocalDate joinDate, LocalDate endDate,
			@NotNull Long subDepartment, @NotNull Long designation, String remarks) {
		super();
		this.employeeId = employeeId;
		this.joinDate = joinDate;
		this.endDate = endDate;
		this.subDepartment = subDepartment;
		this.designation = designation;
		this.remarks = remarks;
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

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public Long getWorkingType() {
		return workingType;
	}

	public void setWorkingType(Long workingType) {
		this.workingType = workingType;
	}

	public Long getJobType() {
		return jobType;
	}

	public void setJobType(Long jobType) {
		this.jobType = jobType;
	}

	public Long getBranch() {
		return branch;
	}

	public void setBranch(Long branch) {
		this.branch = branch;
	}

	public Long getGrade() {
		return grade;
	}

	public void setGrade(Long grade) {
		this.grade = grade;
	}
	
	
}
