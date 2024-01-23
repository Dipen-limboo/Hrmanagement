package com.humanresourcemanagement.ResourceMangement.Payload.requestDto;

public class ChangeRoleDto {
	private Long id;
	
	private int department;
	
	private int designation;

	public ChangeRoleDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ChangeRoleDto(Long id, int department, int designation) {
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

	public int getDepartment() {
		return department;
	}

	public void setDepartment(int department) {
		this.department = department;
	}

	public int getDesignation() {
		return designation;
	}

	public void setDesignation(int designation) {
		this.designation = designation;
	}
	
	
}
