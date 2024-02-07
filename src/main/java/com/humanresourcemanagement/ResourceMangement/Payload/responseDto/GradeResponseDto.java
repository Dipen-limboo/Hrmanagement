package com.humanresourcemanagement.ResourceMangement.Payload.responseDto;

public class GradeResponseDto {
	private Long grade_id;
	private String grade_type;
	private Long department_id;
	public GradeResponseDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public GradeResponseDto(Long grade_id, String grade_type, Long department_id) {
		super();
		this.grade_id = grade_id;
		this.grade_type = grade_type;
		this.department_id = department_id;
	}
	public Long getGrade_id() {
		return grade_id;
	}
	public void setGrade_id(Long grade_id) {
		this.grade_id = grade_id;
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
