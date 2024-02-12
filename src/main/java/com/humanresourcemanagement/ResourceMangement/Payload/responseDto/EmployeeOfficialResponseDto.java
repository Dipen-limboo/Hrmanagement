package com.humanresourcemanagement.ResourceMangement.Payload.responseDto;

public class EmployeeOfficialResponseDto {
	private String employee_official_id;
	
	private Long user_id;
	
	private Long designation_id;
	
	private Long section_id;
	
	private Long branch_id;
	
	private Long working_type_id;
	
	private Long job_type_id;

	public EmployeeOfficialResponseDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EmployeeOfficialResponseDto(String employee_official_id, Long user_id, Long designation_id, Long section_id,
			Long branch_id, Long working_type_id, Long job_type_id) {
		super();
		this.employee_official_id = employee_official_id;
		this.user_id = user_id;
		this.designation_id = designation_id;
		this.section_id = section_id;
		this.branch_id = branch_id;
		this.working_type_id = working_type_id;
		this.job_type_id = job_type_id;
	}

	public String getEmployee_official_id() {
		return employee_official_id;
	}

	public void setEmployee_official_id(String employee_official_id) {
		this.employee_official_id = employee_official_id;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public Long getDesignation_id() {
		return designation_id;
	}

	public void setDesignation_id(Long designation_id) {
		this.designation_id = designation_id;
	}

	public Long getSection_id() {
		return section_id;
	}

	public void setSection_id(Long section_id) {
		this.section_id = section_id;
	}

	public Long getBranch_id() {
		return branch_id;
	}

	public void setBranch_id(Long branch_id) {
		this.branch_id = branch_id;
	}

	public Long getWorking_type_id() {
		return working_type_id;
	}

	public void setWorking_type_id(Long working_type_id) {
		this.working_type_id = working_type_id;
	}

	public Long getJob_type_id() {
		return job_type_id;
	}

	public void setJob_type_id(Long job_type_id) {
		this.job_type_id = job_type_id;
	}
	
	
}
