package com.humanresourcemanagement.ResourceMangement.Payload.requestDto;

public class DesignationDto {
	private String name;
	
	private Long department;

	public DesignationDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DesignationDto(String name, Long department) {
		super();
		this.name = name;
		this.department = department;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getDepartment() {
		return department;
	}

	public void setDepartment(Long department) {
		this.department = department;
	}
	
	
}
