package com.humanresourcemanagement.ResourceMangement.Payload.responseDto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public class WeekendResponseDto {
	private Long weekend_id;
	
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate weekend_date;
	
	private String employee_id;

	public WeekendResponseDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public WeekendResponseDto(Long weekend_id, LocalDate weekend_date, String employee_id) {
		super();
		this.weekend_id = weekend_id;
		this.weekend_date = weekend_date;
		this.employee_id = employee_id;
	}

	public Long getWeekend_id() {
		return weekend_id;
	}

	public void setWeekend_id(Long weekend_id) {
		this.weekend_id = weekend_id;
	}

	public LocalDate getWeekend_date() {
		return weekend_date;
	}

	public void setWeekend_date(LocalDate weekend_date) {
		this.weekend_date = weekend_date;
	}

	public String getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(String employee_id) {
		this.employee_id = employee_id;
	}
	
	
}
