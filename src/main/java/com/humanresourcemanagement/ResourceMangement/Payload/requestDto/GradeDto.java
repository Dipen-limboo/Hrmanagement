package com.humanresourcemanagement.ResourceMangement.Payload.requestDto;

public class GradeDto {
	private String grade_type;
	
	private Long department_id;

	public GradeDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GradeDto(String grade_type, Long department_id) {
		super();
		this.grade_type = grade_type;
		this.department_id = department_id;
	}

	public String getGrade_type() {
		return grade_type;
	}

	public void setGrade_type(String grade_type) {
		this.grade_type = grade_type;
	}

	public Long getDepartment_id() {
		return department_id;
	}

	public void setDepartment_id(Long department_id) {
		this.department_id = department_id;
	}
	
	
}
