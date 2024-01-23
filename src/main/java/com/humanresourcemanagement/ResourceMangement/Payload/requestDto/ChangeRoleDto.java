package com.humanresourcemanagement.ResourceMangement.Payload.requestDto;

import java.time.LocalDate;
import java.util.Set;

public class ChangeRoleDto {
	private Long id;
	
	private Set<String>	 role;
	
	private LocalDate joinDate;
	
	private LocalDate endDate; 
	
	private Long department;
	
	private Long designation;

	public ChangeRoleDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ChangeRoleDto(Long id, Long department, Long designation) {
		super();
		this.id = id;
		this.department = department;
		this.designation = designation;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Set<String> getRole() {
		return role;
	}

	public void setRole(Set<String> role) {
		this.role = role;
	}

	public LocalDate getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(LocalDate joinDate) {
		this.joinDate = joinDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	
	
}
