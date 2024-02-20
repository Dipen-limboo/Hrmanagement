package com.humanresourcemanagement.ResourceMangement.Payload.requestDto;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class RoasterUpdateDto {
	@JsonFormat(pattern="HH:mm:ss")
	private LocalTime startTime;
	
	@JsonFormat(pattern="HH:mm:ss")
	private LocalTime endTime;
	
	private Long organization_id;

	public RoasterUpdateDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RoasterUpdateDto(LocalTime startTime, LocalTime endTime, Long organization_id) {
		super();
		this.startTime = startTime;
		this.endTime = endTime;
		this.organization_id = organization_id;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	public Long getOrganization_id() {
		return organization_id;
	}

	public void setOrganization_id(Long organization_id) {
		this.organization_id = organization_id;
	}
	
	
}
