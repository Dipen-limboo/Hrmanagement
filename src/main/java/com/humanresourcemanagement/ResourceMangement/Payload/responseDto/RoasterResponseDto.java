package com.humanresourcemanagement.ResourceMangement.Payload.responseDto;

import java.time.LocalTime;

public class RoasterResponseDto {

	private Long timeSheet_id;
	
	private LocalTime start_time;
	
	private LocalTime end_time;
	
	private Long organization_id;

	public RoasterResponseDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RoasterResponseDto(Long timeSheet_id, LocalTime start_time, LocalTime end_time, Long organization_id) {
		super();
		this.timeSheet_id = timeSheet_id;
		this.start_time = start_time;
		this.end_time = end_time;
		this.organization_id = organization_id;
	}

	public Long getTimeSheet_id() {
		return timeSheet_id;
	}

	public void setTimeSheet_id(Long timeSheet_id) {
		this.timeSheet_id = timeSheet_id;
	}

	public LocalTime getStart_time() {
		return start_time;
	}

	public void setStart_time(LocalTime start_time) {
		this.start_time = start_time;
	}

	public LocalTime getEnd_time() {
		return end_time;
	}

	public void setEnd_time(LocalTime end_time) {
		this.end_time = end_time;
	}

	public Long getOrganization_id() {
		return organization_id;
	}

	public void setOrganization_id(Long organization_id) {
		this.organization_id = organization_id;
	}
	
	
}
