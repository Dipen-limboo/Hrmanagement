package com.humanresourcemanagement.ResourceMangement.Payload.responseDto;

public class JobTypeResponseDto {
	private Long job_type_id;
	
	private String job_type_name;

	public JobTypeResponseDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public JobTypeResponseDto(Long job_type_id, String job_type_name) {
		super();
		this.job_type_id = job_type_id;
		this.job_type_name = job_type_name;
	}

	public Long getJob_type_id() {
		return job_type_id;
	}

	public void setJob_type_id(Long job_type_id) {
		this.job_type_id = job_type_id;
	}

	public String getJob_type_name() {
		return job_type_name;
	}

	public void setJob_type_name(String job_type_name) {
		this.job_type_name = job_type_name;
	}
	
	
}
