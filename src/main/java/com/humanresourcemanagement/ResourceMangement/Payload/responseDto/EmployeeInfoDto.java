package com.humanresourcemanagement.ResourceMangement.Payload.responseDto;

import java.time.LocalDate;

public class EmployeeInfoDto {
	private Long id;
	private String username;
	private String email;
	private String department;
	private String designation;
	private String approvedBy;
	private LocalDate joinDate;
	private LocalDate leaveDate;
	public EmployeeInfoDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public EmployeeInfoDto(Long id, String username, String email, String department, String designation,
			String approvedBy, LocalDate joinDate, LocalDate leaveDate) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.department = department;
		this.designation = designation;
		this.approvedBy = approvedBy;
		this.joinDate = joinDate;
		this.leaveDate = leaveDate;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
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
	
}
