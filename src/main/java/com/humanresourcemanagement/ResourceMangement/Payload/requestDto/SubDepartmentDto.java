package com.humanresourcemanagement.ResourceMangement.Payload.requestDto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class SubDepartmentDto {
	@NotEmpty
	private String name;
	
	@NotNull
	private Long department_id;

	public SubDepartmentDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SubDepartmentDto(@NotEmpty String name, @NotNull Long department_id) {
		super();
		this.name = name;
		this.department_id = department_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getDepartment_id() {
		return department_id;
	}

	public void setDepartment_id(Long department_id) {
		this.department_id = department_id;
	}
	
	
}
