package com.humanresourcemanagement.ResourceMangement.Payload.requestDto;

public class OffiEmployeeUpdateDto {
	private Long working_type_id;
	
	private Long job_type_id;

	public OffiEmployeeUpdateDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OffiEmployeeUpdateDto(Long working_type_id, Long job_type_id) {
		super();
		this.working_type_id = working_type_id;
		this.job_type_id = job_type_id;
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
