package com.humanresourcemanagement.ResourceMangement.Payload.responseDto;

public class WorkTypeResponseDto {
	private Long work_type_id;
	
	private String work_type;

	public WorkTypeResponseDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public WorkTypeResponseDto(Long work_type_id, String work_type) {
		super();
		this.work_type_id = work_type_id;
		this.work_type = work_type;
	}

	public Long getWork_type_id() {
		return work_type_id;
	}

	public void setWork_type_id(Long work_type_id) {
		this.work_type_id = work_type_id;
	}

	public String getWork_type() {
		return work_type;
	}

	public void setWork_type(String work_type) {
		this.work_type = work_type;
	}
	
	
}
